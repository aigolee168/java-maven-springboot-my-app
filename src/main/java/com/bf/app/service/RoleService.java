package com.bf.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bf.app.constant.AuthType;
import com.bf.app.entities.Authority;
import com.bf.app.entities.Role;
import com.bf.app.repository.RoleRepository;
import com.bf.app.util.Arrays;
import com.bf.app.util.Collections;
import com.bf.app.util.Strings;
import com.bf.app.util.Trees;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    private AuthorityService authorityService;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    public List<Role> findAllByUserId(int userId) {
        return roleRepository.findAllByUserId(userId);
    }

    public Set<Authority> getAuthTree(int roleId) {
        List<Authority> staticAuthList = authorityService.findAllByRoleId(roleId);
        Role role = roleRepository.findById(roleId).get();
        if (Strings.isNotEmpty(role.getAuthPattern())) {
            Set<Authority> fullTree = authorityService.getDescendentAuthTree(-1);
            markTree(fullTree, staticAuthList);

            List<AuthPattern> patterns = parseAuthPattern(role.getAuthPattern());

            List<AuthPattern> includePatterns = Collections.findAll(patterns, it -> it.marked);
            includePatterns.forEach(it -> markInclude(fullTree, it.path, 0));

            List<AuthPattern> excludePatterns = Collections.findAll(patterns, it -> !it.marked);
            excludePatterns.forEach(it -> markExclude(fullTree, it.path, 0));

            Trees.trimTree(fullTree);
            return fullTree;
        } else {
            return Trees.list2tree(new HashSet<>(staticAuthList), -1);
        }
    }

    private static void markTree(Set<Authority> tree, List<Authority> authList) {
        tree.forEach(item -> {
            Authority auth = Collections.findFirst(authList, it -> it.getId().equals(item.getId()));
            if (auth != null) {
                item.setMarked(true);
                markTree(item.getChildren(), authList);
            }
        });
    }

    // ??????:
    // +gm/*sys*/kpi/**
    // -gm/*sys*/kpiSummary
    // ??????:
    // *???????????????????????????
    // **???????????????????????????,????????????????????????????????????
    // *sys*????????????????????????????????????????????????,?????????????????????
    /**
     * ????????????????????????
     * 
     * @param authPattern ????????????????????????,????????????????????????,???????????????????????????:<br>
     *                    line pattern = [+/-](item pattern)/(item pattern)/...<br>
     *                    item pattern = * | ** | *sys*
     * @return ????????????????????????
     */
    private static List<AuthPattern> parseAuthPattern(String authPattern) {
        List<AuthPattern> list = new ArrayList<>();
        for (String line : authPattern.split("\\n")) {
            if (Strings.isEmpty(line))
                continue;
            String prefix = line.substring(0, 1);
            boolean include = !prefix.equals("-");
            String[] path;
            if (prefix == "-" || prefix == "+") {
                path = line.substring(1).split("/");
            } else {
                path = line.split("/");
            }

            List<String> nullPath = Arrays.findAll(path, it -> Strings.isEmpty(it));
            if (!nullPath.isEmpty())
                throw new IllegalArgumentException("????????????????????????????????????");

            List<String> stars = Arrays.findAll(path, it -> "**".equals(it));
            int indexOfStar = Arrays.indexOf(path, "**");
            if (stars.size() > 1 || (stars.size() == 1 && indexOfStar != path.length - 1))
                throw new IllegalArgumentException("**????????????????????????????????????????????????");

            List<String> sys = Arrays.findAll(path, it -> "*sys*".equals(it));
            if (sys.size() > 1)
                throw new IllegalArgumentException("*sys*????????????????????????");

            list.add(new AuthPattern(include, path));
        }
        return list;
    }

    @RequiredArgsConstructor
    private static class AuthPattern {
        final boolean marked;
        @NonNull
        final String[] path;
    }

    private static void markInclude(Set<Authority> tree, String[] path, int curIndex) {
        String pathItem = path[curIndex];
        for (Authority node : tree) {
            switch (pathItem) {
            case "*":
                node.setMarked(true);
                markInclude(node.getChildren(), path, curIndex + 1);
                break;
            case "**":
                node.setMarked(true);
                markInclude(node.getChildren(), path, curIndex);
                break;
            case "*sys*":
                if (node.getType() == AuthType.CONTAINER.getValue() || node.getType() == AuthType.SYSTEM.getValue()) {
                    node.setMarked(true);
                    markInclude(node.getChildren(), path, curIndex);
                }
                break;
            default:
                if (node.getCode().equals(pathItem)) {
                    node.setMarked(true);
                    markInclude(node.getChildren(), path, curIndex + 1);
                }
            }
        }
    }

    private static void markExclude(Set<Authority> tree, String[] path, int curIndex) {
        String pathItem = path[curIndex];
        for (Authority node : tree) {
            boolean matched = false;
            switch (pathItem) {
            case "*":
            case "**":
                matched = true;
                break;
            case "*sys*":
                if (node.getType() == AuthType.CONTAINER.getValue() ||
                        node.getType() == AuthType.SYSTEM.getValue())
                    matched = true;
                break;
            default:
                if (node.getCode().equals(pathItem))
                    matched = true;
            }
            if (matched) {
                if (curIndex == path.length - 1) {
                    node.setMarked(false);
                    markExclude(node.getChildren(), path, curIndex);
                } else {
                    markExclude(node.getChildren(), path, curIndex + 1);
                }
            }
        }
    }

}

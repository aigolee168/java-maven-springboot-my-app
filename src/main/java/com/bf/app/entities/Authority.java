package com.bf.app.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties("marked")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@ToString(exclude = "children")
@Entity
public class Authority {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String code;

    @NonNull
    private String text;

    @NonNull
    private Byte type;

    private Long parentId = -1L;

    private transient final Set<Authority> children = new HashSet<>();

    private transient boolean marked;

}

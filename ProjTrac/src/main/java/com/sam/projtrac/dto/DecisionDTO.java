package com.sam.projtrac.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DecisionDTO {
	@JsonProperty("PRD_ID")
    private long prdId;
    @JsonProperty("COMMENT")
    private String COMMENT;
    @JsonProperty("tarVer")
    private String tarVer;
}

package com.poc.techvoice.articleservice.domain.entities.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {

    private ResponseHeader responseHeader;
}

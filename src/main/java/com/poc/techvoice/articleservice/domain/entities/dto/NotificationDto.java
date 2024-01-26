package com.poc.techvoice.articleservice.domain.entities.dto;

import com.poc.techvoice.articleservice.domain.enums.Action;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {

    private String category;
    private Action action;
    private String articleTitle;
}

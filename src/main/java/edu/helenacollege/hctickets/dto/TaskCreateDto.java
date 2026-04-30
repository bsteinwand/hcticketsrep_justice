package edu.helenacollege.hctickets.dto;

import java.time.OffsetDateTime;

public record TaskCreateDto(
        String name,
        String details,
        Integer ticketId,
        Integer userId,
        String status,
        String priority,
        OffsetDateTime dueDate
) {}
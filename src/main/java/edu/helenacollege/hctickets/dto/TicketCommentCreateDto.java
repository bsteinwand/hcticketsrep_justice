package edu.helenacollege.hctickets.dto;

public record TicketCommentCreateDto(
        Integer ticketId,
        Integer userId,
        String commentText,
        Boolean internalComment,
        Integer parentCommentId
) {
	public TicketCommentCreateDto() {
		this(0, 0, "", true, 0);
	}
	public TicketCommentCreateDto(Integer ticketId, Integer userId) {
		this(ticketId, userId, "", true, 0);
	}
}
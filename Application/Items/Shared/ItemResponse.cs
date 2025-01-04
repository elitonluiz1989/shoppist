namespace Application.Items.Shared;

public record ItemResponse(Guid Id, string Title, DateTimeOffset CreatedAt, DateTimeOffset UpdatedAt);
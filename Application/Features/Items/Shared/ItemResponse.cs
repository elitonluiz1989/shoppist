namespace Application.Features.Items.Shared;

public record ItemResponse(Guid Id, string Title, DateTimeOffset CreatedAt, DateTimeOffset UpdatedAt);
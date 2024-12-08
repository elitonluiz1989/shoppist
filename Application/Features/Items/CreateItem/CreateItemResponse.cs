namespace Application.Features.Items.CreateItem;

public sealed record CreateItemResponse(Guid Id, string Title, DateTimeOffset CreatedAt, DateTimeOffset UpdatedAt);
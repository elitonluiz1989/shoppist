using Application.Features.Items.Shared;

namespace Application.Features.Items.UpdateItem;

public sealed record UpdateItemRequest(Guid Id, string Title) : ItemRequest(Title);
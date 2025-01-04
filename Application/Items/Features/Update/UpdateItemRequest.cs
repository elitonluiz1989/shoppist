using Application.Items.Shared;

namespace Application.Items.Features.Update;

public sealed record UpdateItemRequest(Guid Id, string Title) : ItemRequest(Title);
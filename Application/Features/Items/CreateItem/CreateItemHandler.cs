using Application.Shared.Handlers;
using Application.Shared.Results;
using Domain.Entities;
using Domain.Interfaces;
using Domain.Interfaces.Base;

namespace Application.Features.Items.CreateItem;

public sealed class CreateItemHandler(IUnitOfWork unitOfWork, IItemRepository itemRepository)
    : CreateHandler<Item, CreateItemRequest, CreateItemResponse>(unitOfWork, itemRepository)
        , ICreateItemHandler
{
    protected override Result<CreateItemResponse> Validate(CreateItemRequest request) =>
        new CreateItemValidator().ValidateRequest(request);

    protected override Item CreateEntity(CreateItemRequest request) => request.ToItem();

    protected override CreateItemResponse CreateResponse(Item entity) => entity.ToCreateItemResponse();
}
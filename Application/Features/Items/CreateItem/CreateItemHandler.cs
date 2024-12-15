using Application.Features.Items.CreateItem.Interfaces;
using Application.Shared.Handlers;
using Application.Shared.Results;
using Domain.Entities;
using Domain.Interfaces;
using Domain.Interfaces.Base;

namespace Application.Features.Items.CreateItem;

public sealed class CreateItemHandler(
    IUnitOfWork unitOfWork,
    IItemRepository repository,
    ICreateItemValidator validator
)
    : CreateHandler<Item, CreateItemRequest, CreateItemResponse>(unitOfWork, repository)
        , ICreateItemHandler
{
    protected override Result<CreateItemResponse> Validate(CreateItemRequest request) =>
        validator.ValidateRequest(request);

    protected override Item CreateEntity(CreateItemRequest request) => request.ToItem();

    protected override CreateItemResponse CreateResponse(Item entity) => entity.ToCreateItemResponse();
}
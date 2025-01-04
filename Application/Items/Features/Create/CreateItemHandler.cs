using Application.Items.Interfaces;
using Application.Items.Shared;
using Application.Shared.Handlers;
using Domain.Entities;
using Domain.Interfaces;
using Domain.Interfaces.Base;

namespace Application.Items.Features.Create;

public sealed class CreateItemHandler(
    IUnitOfWork unitOfWork,
    ICreateItemValidator validator,
    IItemRepository repository
)
    : CreateHandler<Item, ItemRequest, ItemResponse>(unitOfWork, validator, repository)
        , ICreateItemHandler
{
    protected override Item CreateEntity(ItemRequest request) => request.ToItem();

    protected override ItemResponse CreateResponse(Item entity) => entity.ToItemResponse();
}
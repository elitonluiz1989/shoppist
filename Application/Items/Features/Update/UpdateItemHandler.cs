using Application.Items.Interfaces;
using Application.Items.Shared;
using Application.Shared.Handlers;
using Application.Shared.Results;
using Domain.Entities;
using Domain.Interfaces;
using Domain.Interfaces.Base;

namespace Application.Items.Features.Update;

public sealed class UpdateItemHandler(
    IUnitOfWork unitOfWork,
    IUpdateItemValidator validator,
    IItemRepository repository
)
    : UpdateHandler<Item, UpdateItemRequest, ItemResponse>(unitOfWork, validator, repository)
        , IUpdateItemHandler
{
    protected override async Task<Result<Item?>> GetEntityUpdated(UpdateItemRequest request,
        CancellationToken cancellationToken)
    {
        Item? item = await repository.FindAsync(request.Id, cancellationToken);

        if (item is null)
            return Result<Item?>.CreateFailure(ItemErrors.ItemNotFound(request.Id));

        item.Title = request.Title;

        return Result<Item?>.CreateSuccess(item);
    }

    protected override ItemResponse CreateResponse(Item entity) => entity.ToItemResponse();
}
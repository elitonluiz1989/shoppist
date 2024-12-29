using Application.Features.Items.CreateItem.Interfaces;
using Application.Features.Items.Shared;

namespace Application.Features.Items.CreateItem;

public sealed class CreateItemValidator : ItemValidator<ItemRequest>, ICreateItemValidator
{
}
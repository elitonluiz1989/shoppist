using Application.Items.Interfaces;
using Application.Items.Shared;

namespace Application.Items.Features.Create;

public sealed class CreateItemValidator : ItemValidator<ItemRequest>, ICreateItemValidator
{
}
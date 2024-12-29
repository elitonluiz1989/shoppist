using Application.Features.Items.Shared;
using Application.Shared.Validators;

namespace Application.Features.Items.CreateItem.Interfaces;

public interface ICreateItemValidator : IRequestValidator<ItemRequest, ItemResponse>
{
}
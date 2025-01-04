using Application.Items.Shared;
using Application.Shared.Validators;

namespace Application.Items.Interfaces;

public interface ICreateItemValidator : IRequestValidator<ItemRequest, ItemResponse>
{
}
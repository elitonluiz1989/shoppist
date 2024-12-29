using Application.Features.Items.Shared;
using Application.Shared.Validators;

namespace Application.Features.Items.UpdateItem.Interfaces;

public interface IUpdateItemValidator : IRequestValidator<UpdateItemRequest, ItemResponse>
{
}
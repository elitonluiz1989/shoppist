using Application.Items.Features.Update;
using Application.Items.Shared;
using Application.Shared.Interfaces;

namespace Application.Items.Interfaces;

public interface IUpdateItemValidator : IRequestValidator<UpdateItemRequest, ItemResponse>
{
}
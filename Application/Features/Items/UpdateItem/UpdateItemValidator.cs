using Application.Features.Items.Shared;
using Application.Features.Items.UpdateItem.Interfaces;
using FluentValidation;

namespace Application.Features.Items.UpdateItem;

public sealed class UpdateItemValidator : ItemValidator<UpdateItemRequest>, IUpdateItemValidator
{
    public UpdateItemValidator()
    {
        RuleFor(x => x.Id).NotEmpty();
    }
}
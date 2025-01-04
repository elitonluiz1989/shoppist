using Application.Items.Interfaces;
using Application.Items.Shared;
using FluentValidation;

namespace Application.Items.Features.Update;

public sealed class UpdateItemValidator : ItemValidator<UpdateItemRequest>, IUpdateItemValidator
{
    public UpdateItemValidator()
    {
        RuleFor(x => x.Id).NotEmpty();
    }
}
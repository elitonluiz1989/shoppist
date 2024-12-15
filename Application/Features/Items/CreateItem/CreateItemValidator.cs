using Application.Features.Items.CreateItem.Interfaces;
using Application.Shared.Validators;
using FluentValidation;

namespace Application.Features.Items.CreateItem;

public sealed class CreateItemValidator : RequestValidator<CreateItemRequest, CreateItemResponse>, ICreateItemValidator
{
    public CreateItemValidator()
    {
        RuleFor(x => x.Title)
            .NotEmpty()
            .MaximumLength(100);
    }
}
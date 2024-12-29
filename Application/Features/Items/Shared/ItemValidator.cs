using Application.Shared.Validators;
using FluentValidation;

namespace Application.Features.Items.Shared;

public abstract class ItemValidator<TRequest> : RequestValidator<TRequest, ItemResponse>
    where TRequest : ItemRequest
{
    protected ItemValidator()
    {
        RuleFor(x => x.Title)
            .NotEmpty()
            .MaximumLength(100);
    }
}
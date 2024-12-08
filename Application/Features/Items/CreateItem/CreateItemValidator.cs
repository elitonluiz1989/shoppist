using Application.Shared.Requests;
using FluentValidation;

namespace Application.Features.Items.CreateItem;

public sealed class CreateItemValidator : RequestValidator<CreateItemRequest, CreateItemResponse>
{
    public CreateItemValidator()
    {
        RuleFor(x => x.Title)
            .NotEmpty()
            .MaximumLength(100);
    }
}
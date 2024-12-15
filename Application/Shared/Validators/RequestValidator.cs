using System.Collections.Immutable;
using Application.Shared.Results;
using FluentValidation;

namespace Application.Shared.Validators;

public abstract class RequestValidator<TRequest, TResponse>
    : AbstractValidator<TRequest>
        , IRequestValidator<TRequest, TResponse>
{
    public Result<TResponse> ValidateRequest(TRequest request)
    {
        var results = Validate(request);

        if (results.IsValid)
            return Result<TResponse>.CreateSuccess();

        ImmutableArray<ErrorResult> errors =
        [
            ..results.Errors
                .Select(p => new ErrorResult(p.ErrorCode, p.ErrorMessage))
        ];

        return Result<TResponse>.CreateFailure(errors);
    }
}
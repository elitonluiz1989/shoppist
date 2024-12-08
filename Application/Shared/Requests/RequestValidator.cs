using System.Collections.Immutable;
using Application.Shared.Results;
using FluentValidation;

namespace Application.Shared.Requests;

public abstract class RequestValidator<TRequest, TResponse> : AbstractValidator<TRequest>
{
    public Result<TResponse> ValidateRequest(TRequest request)
    {
        var results = Validate(request);

        if (results.IsValid)
            return Result<TResponse>.CreateSuccess();

        var errors = results.Errors
            .Select(p => new ErrorResult(p.ErrorCode, p.ErrorMessage))
            .ToImmutableArray();

        return Result<TResponse>.CreateFailure(errors);
    }
}
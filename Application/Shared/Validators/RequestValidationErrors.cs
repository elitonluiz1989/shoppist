using Application.Shared.Results;

namespace Application.Shared.Validators;

public static class RequestValidationErrors
{
    public static readonly ErrorResult RequestIsNull =
        new ErrorResult("Request.Validation.RequestIsNull", "Request is null");
}
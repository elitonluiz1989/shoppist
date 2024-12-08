using Application.Shared.Results;

namespace Application.Shared.Requests;

public static class RequestValidationErrors
{
    public static readonly ErrorResult RequestIsNull =
        new ErrorResult("Request.Validation.RequestIsNull", "Request is null");
}
using Application.Shared.Results;

namespace Application.Shared.Validators;

public static class RequestValidationErrors
{
    public static readonly ErrorResult RequestIsNull =
        new("Request.Validation.RequestIsNull", "Request is null");

    public static readonly ErrorResult EntityNotFoundToDelete =
        new("Request.Validation.RequestIsNull", "Entity not found to delete");
}
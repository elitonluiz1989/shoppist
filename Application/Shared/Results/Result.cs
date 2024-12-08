using System.Collections.Immutable;

namespace Application.Shared.Results;

public class Result<TValue>
{
    private Result(ImmutableArray<ErrorResult> errors)
    {
        IsSuccess = false;
        Errors = errors;
    }

    private Result(TValue? value)
    {
        Value = value;
        IsSuccess = true;
        Errors = [];
    }

    public TValue? Value { get; private set; }
    public ImmutableArray<ErrorResult> Errors { get; init; }

    public bool IsSuccess { get; set; }

    public bool IsFailure => !IsSuccess;

    public static Result<TValue> CreateSuccess(TValue value) => new(value);
    public static Result<TValue> CreateSuccess() => new(default(TValue));
    public static Result<TValue> CreateFailure(ImmutableArray<ErrorResult> errors) => new(errors);
    public static Result<TValue> CreateFailure(ErrorResult error) => new([error]);
}
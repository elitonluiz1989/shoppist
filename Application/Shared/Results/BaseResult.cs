using System.Collections.Immutable;

namespace Application.Shared.Results;

public abstract class BaseResult<TResult>(ImmutableArray<ErrorResult> errors)
    where TResult : BaseResult<TResult>
{
    public ImmutableArray<ErrorResult> Errors { get; } = errors;

    public bool IsSuccess => Errors.Length == 0;

    public bool IsFailure => !IsSuccess;

    public static TResult CreateFailure(ImmutableArray<ErrorResult> errors) =>
        (Activator.CreateInstance(typeof(TResult), errors) as TResult)!;

    public static TResult CreateFailure(ErrorResult error) => CreateFailure([error]);
}
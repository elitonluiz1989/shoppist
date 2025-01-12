using System.Collections.Immutable;

namespace Application.Shared.Results;

public class Result(ImmutableArray<ErrorResult> errors) : BaseResult<Result>(errors)
{
    public Result() : this(ImmutableArray<ErrorResult>.Empty)
    {
    }

    public static Result CreateSuccess() => new();
}

public class Result<TValue>(ImmutableArray<ErrorResult> errors) : BaseResult<Result<TValue>>(errors)
{
    public Result(TValue? value) : this(ImmutableArray<ErrorResult>.Empty)
    {
        Value = value;
    }

    public TValue? Value { get; private set; }

    public static Result<TValue> CreateSuccess(TValue value) => new(value);

    public static Result<TValue> CreateSuccess() => new(default(TValue));
}
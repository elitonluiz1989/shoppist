﻿using Application.Shared.Results;
using FluentValidation;

namespace Application.Shared.Interfaces;

public interface IRequestValidator<TRequest, TResponse> : IValidator<TRequest>
{
    Result<TResponse?> ValidateRequest(TRequest? request);
}
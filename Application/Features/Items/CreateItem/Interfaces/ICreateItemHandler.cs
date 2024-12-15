﻿using Application.Shared.Interfaces;

namespace Application.Features.Items.CreateItem.Interfaces;

public interface ICreateItemHandler : IRequestHandler<CreateItemRequest, CreateItemResponse>
{
}
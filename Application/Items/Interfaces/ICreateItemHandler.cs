using Application.Items.Shared;
using Application.Shared.Interfaces;

namespace Application.Items.Interfaces;

public interface ICreateItemHandler : IRequestHandler<ItemRequest, ItemResponse>
{
}
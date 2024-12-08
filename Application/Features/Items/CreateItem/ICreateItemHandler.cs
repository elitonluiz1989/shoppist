using Application.Shared.Interfaces;

namespace Application.Features.Items.CreateItem;

public interface ICreateItemHandler : IRequestHandler<CreateItemRequest, CreateItemResponse>
{
}
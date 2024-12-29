using Bogus;

namespace Tests.Helpers;

public sealed class FakerHelper : Faker
{
    public string RandomString(short minLength, short maxLength)
    {
        short length = Random.Short(minLength, maxLength);

        return Random.String(length);
    }
}
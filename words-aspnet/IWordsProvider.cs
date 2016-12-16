namespace words_aspnet
{
    public interface IWordsProvider
    {
        string Noun { get; }
        string Adjective { get; }
        string Verb { get; }
    }
}
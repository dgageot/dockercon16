using System;

namespace words_aspnet
{
    public class WordsProvider : IWordsProvider
    {
        static readonly string[] Nouns = new[] { "dead body", "elephant", "go language", "laptop", "container", "micro-service", "turtle", "whale" };
        static readonly string[] Adjectives = new[] { "the exquisite", "a pink", "the rotten", "a red", "the floating", "a broken", "a shiny", "the pretty", "the impressive", "an awesome" };
        static readonly string[] Verbs = new[] { "will drink", "smashes", "smokes", "eats", "walks towards", "loves", "helps", "pushes", "debugs" };
        private readonly Random _random = new Random(DateTime.Now.Millisecond);
        private readonly string _noun;
        private readonly string _adjective;
        private readonly string _verb;

        public WordsProvider()
        {
            _noun = Nouns[_random.Next(10)];
            _adjective = Adjectives[_random.Next(10)];
            _verb = Verbs[_random.Next(10)];
        }

        public string Noun
        {
            get
            {
                return _noun;
            }
        }

        public string Verb
        {
            get
            {
                return _verb;
            }
        }

        public string Adjective
        {
            get
            {
                return _adjective;
            }
        }
    }
}
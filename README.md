# Kashubian Dictionary — Backend

Spring Boot (Kotlin) backend for the Kashubian–Polish–English dictionary:
GraphQL read API, REST mutation API with HTTP Basic auth, event-sourced
storage on PostgreSQL. Frontend lives in
[kashubian-dictionary-fe](../kashubian-dictionary-fe).

## Stack

- Kotlin, Spring Boot 2.7, Gradle (JDK 17)
- GraphQL (`/graphql`, schema in `src/main/resources/graphql/`)
- Spring Data JPA + jOOQ, Flyway migrations (`src/main/resources/db/migration`)
- PostgreSQL 14 (`docker-compose.yml`, port 5555)
- Event sourcing: every create/update/delete is stored in the `event` table and
  can be replayed on startup (`DataInitializer.recreateDataFromEvents`)

## Running

```bash
docker compose up -d                                   # kas-db on localhost:5555
export DB_URL=jdbc:postgresql://localhost:5555/kas
export DB_USER=kas DB_PASSWORD=kas
export MAIL_HOST=localhost MAIL_PORT=25 MAIL_USER_NAME=x MAIL_PASSWORD=x  # dummy values OK
./gradlew bootRun
```

- `MAIL_*` are mandatory placeholders (the mail starter fails without them;
  connection-refused errors in the log are harmless locally).
- Admin credentials: `AUTH_CREDENTIALS=user;pass;ROLE`, default `admin;admin;ADMIN`.
- Swagger UI at `/swagger-ui.html`, actuator under `/actuator`.

On an empty database the app seeds itself (see below).

## Development seed data

`DataInitializer` (an `ApplicationRunner`) creates **100 real Kashubian words**
from `src/main/resources/seed/kashubian-words.json` — no more EasyRandom/JavaFaker
junk. Entries are created through `KashubianEntryController`, so the event log
stays complete. Seeding runs only when the entry table is empty; with events but
no entries, data is instead replayed from the event table.

### Seed file format

```jsonc
{
  "source": "...provenance note...",
  "words": [
    {
      "word": "rëba",
      "partOfSpeech": "NOUN", "partOfSpeechSubType": "FEMININE",
      "definition": "fish", "polish": "ryba", "english": "fish",
      "origin": "praindoeur ...",              // etymology (optional)
      "variation": { "nounVariation": { "nominative": "...", ... } },
      "examples": [{ "example": "...", "note": "source citation" }],
      "proverbs": [...], "idioms": [...], "quotes": [...],
      "synonymsOf": ["..."], "antonymOf": "...", "hypernymOf": "...",
      "baseOf": "...", "relatedTo": [{ "word": "...", "note": "..." }]
    }
  ]
}
```

Rules:

- **Cross-entry links reference other seed words by name** (`hypernymOf`,
  `antonymOf`, `synonymsOf`, `baseOf`, `relatedTo`). The seeder resolves them to
  database ids, so a referenced word **must appear earlier in the file** than
  the word referencing it; otherwise startup fails fast with a
  "must be created earlier" error.
- Every seed entry gets `priority = true`: the `find_word_of_the_day()` SQL
  function rotates only over priority entries.
- `variation` JSON keys must match the `crud/model/json/` classes for the word's
  subtype (`nounVariation` for nouns, `presentVariations`/… for verbs,
  `base`/`comparative`/`superlative` for adjectives) — the
  `CorrectVariationJsonFormatByPartOfSpeechSubType` validator rejects wrong
  shapes on REST create/update, and the public entry page renders these shapes.
- `initializer.size=100` (application.properties) caps how many words are used —
  keep it ≥ the number of entries in the file.

### Data provenance

All content is real, published material — nothing invented:

- **Headwords**: Kashubian Swadesh list (Wiktionary `Module:Swadesh/data/csb`)
  and Polish–Kashubian dictionaries, in modern standard orthography.
- **Etymologies & collocations**: pl.wiktionary.org csb sections (CC BY-SA 3.0;
  cited in item notes).
- **Example sentences**: shortest real sentence containing the word from
  csb.wikipedia.org (Kaszëbskô Wikipedijia) articles; note cites the article.
- **Proverbs & quotes**: printed sources (Kaszubopedia "Przysłowié",
  E. Rogowska *Acta Cassubiana* IV 2002, H. Derdowski, A. Majkowski).
- **Inflection (`variation`)**: only paradigms published on pl.wiktionary.org
  `{{odmiana}}`/`{{stopn}}` sections are seeded (6 words: full case table for
  *miesąc*, present-tense forms for *spiéwac*, degrees of comparison for
  *dobri*, partial nom./nom.-pl. for *głowa*, *lëst*, *jãzëk*); the rest stay
  `null` because no reliable source exists — nothing is invented.

### Reseeding after editing the seed JSON

Seeding only runs on an empty entry table, so wipe the schema first:

```bash
docker exec kas-db psql -U kas -d kas -c \
  'DROP SCHEMA public CASCADE; CREATE SCHEMA public; GRANT ALL ON SCHEMA public TO kas;'
./gradlew bootRun   # re-runs Flyway + seeds 100 entries
```

### Current coverage (100 words)

| field | entries with data |
|---|---|
| examples | 58 (93 sentences cite csb.wikipedia) |
| etymology (`origin`) | 44 |
| antonyms / see-also | 10 / 10 |
| idioms | 12 |
| hypernyms / synonyms / base form | 6 / 2 / 1 |
| proverbs / quotes | 6 / 2 |
| inflection (`variation`) | 6 (miesąc, głowa, lëst, jãzëk, spiéwac, dobri) |

Words without examples simply had no usable source hit; fake sentences are not
generated on purpose.

## Custom queries & endpoints

- `GET /custom-query/word-of-the-day` — random priority entry, changes twice a day;
  returns the word, its definitions and the entry `note` (the frontend card shows the note)
- `GET /custom-query/variation-example/{partOfSpeechSubType}` — sample inflection
- `POST/PUT/DELETE /kashubian-entry/...` — CRUD (Basic auth), sound-file
  upload/download at `/kashubian-entry/{id}/file`

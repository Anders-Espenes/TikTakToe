# TikTakToe
Prosjektoppgave 2 i Appliklasjonsutvikling
Denne oppgaven går ut på å lage en TicTacToe klient som kobler seg opp mot [GenericGameService](https://github.com/crismo/GenericGameService) Api. Apien er en enkel server som lar klienter lage, koble til spill og oppdatere og hente data.

Klienten er laget for Android med kotlin.


Applikasjonen er delt in i tre deler et view, en manager og en service.

View består av det visuelle i applikasjonen og består av to fragments og tre dialoger. MenyFragment er hovedmenyen hvor en tittel og logo er vist frem, samt to knapper som lar brukeren lage et spill eller bli med et eksisterende spill. GameFragment er viser selve spillet og består av en title, navn på spillerene i lobbyen og en spillebrett. Spillebrettet består av et Gridayout med 9 knapper arrangert i et 3x3 grid. Hver knapp er et felt på brettet og ved trykk skal feltet markeres av spilleren.
Dialogene brukes for å enkelt hente in informasjon fra brukeren og derette gå til neste fragment. CreateGameDialog er en AlertDialog som popper opp når create game knappen på MenuFragment trykkes. Den ber brukeren om å gi et navn/gamertag, og tar deretter brukeren fra MenuFragment til GameFragment. JoinGameDialog er tilsvarende en AlertDialog som popper opp når join game knappen trykkes på MenuFragment. Den ber brukeren om et navn/gamertag og en id for lobbyen brukeren vil bli med. Deretter tar en brukeren fra MenuFragment til GameFragment. Navigasjonen er gjort ved hjelp av navigation components som er automatisk genenererte og enkelt satt sammen.

GameManager er logikken i applikasjonen. Den er laget som et object som medfører at det brukes singelton pattern og bare en GameManager kommer til å eksistere. Den har metoder som bruker GameService for å snakke med APIen og spill logikken som sjekker om en spiller har vunnet. Det brukes livedata for å oppdatere informasjonen som skal vises på viewet, når en endring skjer på livedataen så vil viewet som observerer også gjør endringer for å vise oppdatert data.

GameService er også laget som et object som medfører at bare en eksisterer. Den har metoder som konstruerer nødvendig URLer og metoder som snakker med APIen. Dette inkluderer createGame som som ber om å lage en lobby, joinGame som ber om å bli med i en lobby, updateGame som ber om å updatere dataen i lobbyen og pollGame som ber om å få tilsent dataen i lobbien. Disse metodene er implementert asynkront med callbacks som betyr at applikasjonen ikke trenger å vente på svar fra APIen før den eksekverer videre i koden. Når svar kommer vil applikasjonen hoppe tilbake og håndtere svaret i GameManager.

Ved å skille ifra logikken i GameManager og GameService tjenesten er det mulig å gjenbruke GameService for andre typer spill mot samme API ettersom APIen er enkel og er ment for å bare videreføre data og ikke utføre logikk. Siden GameManager er frakoblet viewet kan denne logikken gjenbrukes hvis det trengs et nytt frontend utsende for andre telefoner eller platformer. 
![menu2](https://user-images.githubusercontent.com/54952804/118196001-8cf03a00-b44c-11eb-8dd0-2fd1e3a26e4c.png)
![gameboard2](https://user-images.githubusercontent.com/54952804/118196003-8d88d080-b44c-11eb-8962-eda7333eb2bf.png)

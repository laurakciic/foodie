# 🥑 Foodie

## Opis aplikacije

Foodie je Android aplikacija koja omogućuje pretraživanje različitih vrsta recepata.   

Korisnici mogu filtrirati recepte prema vrsti obroka (predjelo, glavno jelo, desert, piće, salata, juha, umak, finger food itd.) 
i prema vrsti prehrane (gluten free, vegan, vegetarian, whole30, primal...).   

Aplikacija također omogućuje izravno pretraživanje recepata pomoću tražilice gdje korisnik upisom ključne riječi 
može pronaći odgovarajuće recepte.   

Klikom na pojedini recept, korisnik može vidjeti pregled informacija o receptu, sastojke te instrukcije prikazane iz zasebne 
web stranice.   

Svaki recept moguće je spremiti i ukloniti iz favorita.  

Uz pregled recepata, korisnici u posebnom fragmentu mogu pročitati zanimljive činjenice vezane uz hranu koje mogu podijeliti s prijateljima.  

Aplikacija također podržava rad bez internetske veze uz određena ograničenja.  

## Ograničenja

- bez internet veze korisnik nije u mogućnosti vidjeti činjenice vezane uz hranu  
- bez internet veze korisnik nije u mogućnosti vidjeti detaljne instrukcije sa zasebne web stranice prikazane u aplikaciji  
- bez internet veze korisnik nije u mogućnosti filtrirati i pretraživati recepte
- ukoliko korisnik prvi put pokreće tek instaliranu aplikaciju bez internet veze, nije u mogućnosti vidjeti recepte

## Activities & Fragments

MainActivity predstavlja glavnu aktivnost koja služi kao ulazna točka za interakciju s korisnikom. Iz glavne aktivnosti može se 
pokrenuti DetailsActivity, sekundarna aktivnost.
> Kada glavna aktivnost pokrene drugu aktivnost, tada glavna aktivnost prelazi u stanje pauze. Kada sekundarna aktivnost
završi, glavna aktivnost vraća se u prvi plan i nastavlja.   

- MainActivity - glavna aktivnost, prvi ekran koji se prikazuje kada korisnik pokrene aplikaciju. Sadrži tri fragmenta, od kojih je prvotno prikazan 
Recipes fragment.   
- DetailsActivity - sekundarna aktivnost koja se pokreće klikom na određeni recept. Također sadrži tri fragmenta od kojih je prvotno prikazan Overview s pregledom informacija o receptu   

- RECIPES fragment - sadrži popis recepata, gumb za filtriranje te tražilicu   
- FAVOURITES fragment - sadrži popis spremljenih recepata koji se mogu uklanjati   
- FOOD FACT fragment - random food fact s mogućnošću dijeljenja   

- OVERVIEW fragment - prikazuje pregled osnovnih informacija o receptu   
- INGREDIENTS fragment - prikazuje popis sastojaka potrebnih za recept   
- INSTRUCTIONS fragment - web view koji prikazuje web stranicu na kojoj se nalaze instrukcije za odabrani recept  

# User Stories

Svaki Epic user story podjeljen je u više user storyja, kojima su definirane
funkcionalnosti aplikacije iz perspektive pojedine persone. Foodie zasad ima samo jednu vrstu korisnika. Svaki User story
ima definiran acceptance criteria koji potvrđuje ispunjavanje tog User storija.
Epic user story je ispunjen kada su ispunjeni svi acceptance kriteriji svih
storija unutra tog Epica.

## Epic 1: Prikaz recepata sa [Spoonacular API](https://spoonacular.com/food-api)

- S1-1 Kao korisnik, kada pokrenem aplikaciju, trebam vidjeti početni zaslon aplikacije, odnosno, Recipes fragment

    Acceptance criteria:
    - Korisnik vidi shimmer efekt za vrijeme učitavanja podataka
    - Korisnik vidi popis recepata nakon što se podatci učitaju 
    - Za svaki izlistani recept korisnik vidi njegovu sliku, naslov, opis te tri ikonice koje prikazuju broj sviđanja, vrijeme pravljenja te da li je recept veganski ili ne

- S1-2 Kao korisnik, kada se nalazim na Recipes fragmentu, imam mogućnost filtriranja recepata

    Acceptance criteria:
    - Korisnik vidi zeleni gumb sa znakom restorana
    - Klikom na gumb restorana korisnik može pretraživati recepte prema vrsti obroka i vrsti prehrane

- S1-3 Kao korisnik, kada se nalazim na Recipes fragmentu, imam mogućnost pretraživanja proizvoda
    
    Need: valjana ključna riječ koja se nalazi u naslovu povučenih recepata   

    Acceptance criteria:
    - Korisnik vidi ikonicu tražilice
    - Klikom na ikonicu tražilice, korisnik ima mogućnost pretraživanja recepata upisom ključne riječi

## Epic 2: Detalji o receptu 

- S2-1 Kao korisnik, klikom na pojedini recept, nalazim se u Details activityu te mogu vidjeti više detalja o samom receptu

    Acceptance criteria:
    - Korisnik se mora nalaziti u Recipes ili Favorites fragmentu kako bi kliknio na pojedini recept
    - Klikom na pojedini recept otvara se prvi Overview fragment
    - Overview fragment prikazuje sliku recepta, broj sviđanja, vrijeme pripreme, naslov i opis recepta te opće informacije o receptu

- S2-2 Kao korisnik, kada se nalazim u Details activityu, mogu vidjeti potrebne sastojke za odabrani recept

    Acceptance criteria:
    - Klikom na fragment Ingredients, korisnik vidi popis sastojaka potrebnih za pojedini recept
    - Svaki sastojak ima svoju sliku, naslov, količinu, stanje te puno ime

- S2-3 Kao korisnik, kada se nalazim u Details activityu, mogu vidjeti instrukcije za odabrani recept

    Acceptance criteria:
    - Klikom na fragment Instructions, korisnik vidi web view koji prikazuje web stranicu na kojoj se nalaze instrukcije za odabrani recept 

## Epic 3: Spremanje recepata u favorite

- S3-1 Kao korisnik, kada se nalazim na pojedinom receptu, imam mogućnost spremanja recepta u favorite

    Acceptance criteria: 
    - Korisnik vidi ikonicu zvjezdice
    - Zvjezdica je žuta kada je recept već spremljen i siva kada recept nije spremljen
    - Klikom na ikonicu zvjezdice, recept će se spremiti u favorite ili obrisati iz favorita, ovisno o stanju spremljenosti tj. boji zvjezdice
    - Kada se recept spremi, prikaže se Toast poruka koja daje povratnu informaciju da je recept spremljen

- S3-2 Kao korisnik, kada se nalazim u Favourites fragmentu, vidim pregled spremljenih recepata

    Acceptance criteria:
    - Korisnik vidi prikaz recepata koje je prethodno spremio
    - Ukoliko nema spremljenih recepata, korisnik vidi odgovarajuću sliku i tekst koji daju do znanja da nema spremljenih recepata
    - Klikom na pojedini recept, korisnik se redirecta na Details activity gdje vidi više detalja o odabranom receptu

- S3-3 Kao korisnik, kada se nalazim u Favourites fragmentu ili Details activityu, imam mogućnost uklanjanja spremljenih recepata

    Acceptance criteria:
    - Klikom na žutu ikonicu zvjezdice spremljenog recepta, korisnik uklanja spremljeni recept iz favorita
    - Dužim klikom na recept u Favourites fragmentu, korisnik na taj način može označiti jedan ili više recepata te ih obrisati klikom na ikonicu smeća
    - Klikom na tri točkice u navbaru, korisnik ima mogućnost uklanjanja svih spremljenih recepata
    - Kada je recept uklonjen, prikaže se Toast poruka kao povratna informacija da je recept uklonjen

## Epic 4: Random Food Facts

- S4-1 Kao korisnik, kada kliknem na Food Fact fragment, vidim random food fact činjenicu

    Acceptance criteria:
    - Klikom na Food Fact fragment, korisniku se prikaže random činjenica vezana za hranu

- S4-2 Kao korisnik, kada kliknem na Share ikonicu, imam mogućnost dijeljenja činjenice

    Acceptance criteria:
    - Klikom na Share ikonicu, otvara se popis aplikacija preko kojih mogu podijeliti random food činjenicu  

## Epic 5: Podrška prilikom gubitka internet veze

- S5-1 Kao korisnik, prilikom prvog pokretanja tek instalirane aplikacije bez internet veze, na zaslonu vidim odgovarajući informativni sadržaj

    Acceptance criteria:
    - Korisnik vidi odgovarajuću sliku i tekst koji sugeriraju na gubitak internet veze

- S5-2 Kao korisnik, u slučaju gubitka internet veze, imam mogućnost pregleda recepata 
  
    Acceptance criteria:
    - Klikom na Recipes fragment, korisnik ima mogućnost pregleda prethodno povučenih recepata za vrijeme gubitka internet veze
    - Klikom na pojedini recept, korisnik ima mogućnost pregleda detalja o receptu za vrijeme gubitka internet veze
    - Klikom na Ingredients fragment, korisnik ima mogućnost pregleda sastojaka o receptu za vrijeme gubitka internet veze

- S5-3 Kao korisnik, kada pristupim Food Fact fragmentu za vrijeme gubitka internet veze, na zaslonu vidim odgovarajući sadržaj

    Acceptance criteria:
    - Klikom na Food Fact fragment, u slučaju gubitka internet veze, korisnik vidi zadnje povučenu činjenicu o hrani 
    - Klikom na Food Fact fragment, u slučaju prvog pokretanja tek instalirane aplikacije bez internet veze, korisnik vidi odgovarajuću sliku i tekst koji sugeriraju na gubitak internet veze

- S5-4 Kao korisnik, kada je internet veza ponovno uspostavljena, vidim povratnu informaciju o tome
    
    Acceptance criteria:
    - Korisnik vidi Toast poruku na donjem dijelu zaslona koja daje do znanja da je internet veza ponovno uspostavljena 

    
 ## UML dijagrami

 ### UML Class dijagram

https://app.creately.com/d/ZY5sn41tlDg/edit

![Class Diagram](https://github.com/laurakciic/foodie/blob/master/class-diagram.png)

 ### baza

https://drawsql.app/teams/laura-8/diagrams/recipes
  
   
    
    
    
    
    


  
 
  
 
 
 
 
   
- Recepti
    - model klase (generirane pomoću JSON to Kotlin plugina)
    - FoodRecipesAPI interface za GET requestove (Retrofit)
    - RemoteDataSource klasa koja requesta podatke od API - u nju je ubačen FoodRecipesAPI
    - ...
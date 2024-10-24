# Programsko inženjerstvo

> Ime projekta u naslovu ima cilj opisati namjenu projekta te pomoći u podizanju početnog interesa za projekt prezentirajući osnovnu svrhu projekta.
> Isključivo ovisi o Vama!
> 
> Naravno, nijedan predložak nije idealan za sve projekte jer su potrebe i ciljevi različiti. Ne bojte se naglasiti Vaš cilj u ovoj početnoj stranici projekta, podržat ćemo ga bez obzira usredotočili se Vi više na tenologiju ili marketing.
> 
> Zašto ovaj dokument? Samo manji dio timova je do sada propoznao potrebu (a i meni je lakše pratiti Vaš rad).  

# Opis projekta
Ovaj projekt je reultat timskog rada u sklopu projeknog zadatka kolegija [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz) na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu. 

Kratko opisati cilj Vašeg projekta. Vaša motivacija?  (Napomena: odgovor nije »Zato što je to bio zadatak i nismo imali ideje za drugo.«). Koji problem rješavate?
> Obzirom da je ovo zadani projekt navedite i što želite/jeste novo  naučili.

> Dobro izrađen opis omogućuje vam da pokažete svoj rad drugim programerima, kao i potencijalnim poslodavcima. Ne samo da prvi dojam na stranici opisa često razlikuje dobar projekt od lošeg projekta već i predstavlja dobru praksu koju morate savladati.

# Funkcijski zahtjevi
1. sustav mora omogućiti stvaranje administratora
2. svi korisnici
    - mogu pregledavati termine terena
    - mogu pregledavati turnire temeljem sljedećih kategorija: cijena kotizacije, razina igrača, iznos nagrade
3. anonimni korisnici
    - mogu se registrirati kao vlasnik terena ili kao igrač

4. administratori
    - mogu postavljati cijenu članstva za vlasnike terena
    - mogu stvarati korisnike (igrače i vlasnike terena)
    - mogu pregledavati korisnike
    - mogu mijenjati korisničke podatke
    - mogu brisati korisnike
5. vlasnici terena
    - mogu godišnju članarinu plaćati PayPalom ili kreditnom karticom
    - imaju javni profil sa osnovnim podacima (naziv, adresa, kontakt telefon, popis svih oglašenih terena i turnira)
    - mogu organizirati turnire sljedećim podacima: naziv, lokacija, datum, cijena kotizacije, nagrade, opis
    - turniru čiji je datum došao ili prošao mogu postaviti rezultate i dodati fotografije sa turnira
    - odobravaju prijave igrača za turnire koje su organizirali
6. igrači
    - mogu rezervirati termin terena
    - mogu odabrati način plaćanja rezervacije termina terena (gotovinom prilikom korištenja, PayPalom ili kreditnom karticom)
    - mogu otkazati rezervacije terena ako termin nije u sljedeća 24 sata
    - mogu se prijaviti na otvorene turnire
    - dobivaju obavijest o odobrenoj/odbijenoj prijavi na turnir
    - na odigrane mečeve turnira mogu postavljati komentare i fotografije
    - se mogu pretplatiti na obavijesti o novoobjavljenim turnirima


# Nefunkcijski zahtjevi
1. sustav mora funkcionirati na preglednicima Google Chrome, Firefox i Safari
2. sustav mora za pregled i rezervaciju termina koristiti vanjsku uslugu kalendara
3. sustav mora omogućiti igračima rezervaciju termina u 3 klika od naslovne stranice ne računajući odabir načina plaćanja
4. sustav mora postavljene turnire objavini unutar 5 minuta od postavljanja
5. sustav za prijave na turnir mora u manje od 10 sekundi dati povratnu informaciju (npr. prijava uspješna: čeka se potvrda organizatora)
6. sustav mora biti dostupan 24 sata dnevno
7. sustav treba osigurati zaštitu podataka prilikom plaćanja
8. sustav treba osigurati zaštitu podataka korisnika pri radu s aplikacijom
9. sustav za autentifikaciju mora koristiti OAuth2.0 standard


# Tehnologije
- backend:
  - Python3
  - fastAPI
  - MongoDB
- frontend: 
  - React
  - TypeScript
  - TailwindCSS
  - Next.js

#Instalcija
# Članovi tima 
> Popis članova tima/linkovi/ glavni doprinos
>

# Kontribucije
>Pravila ovise o organizaciji tima i su često izdvojena u CONTRIBUTING.md



# 📝 Kodeks ponašanja [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)
Kao studenti sigurno ste upoznati s minimumom prihvatljivog ponašanja definiran u [KODEKS PONAŠANJA STUDENATA FAKULTETA ELEKTROTEHNIKE I RAČUNARSTVA SVEUČILIŠTA U ZAGREBU](https://www.fer.hr/_download/repository/Kodeks_ponasanja_studenata_FER-a_procisceni_tekst_2016%5B1%5D.pdf), te dodatnim naputcima za timski rad na predmetu [Programsko inženjerstvo](https://wwww.fer.hr).
Očekujemo da ćete poštovati [etički kodeks IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html) koji ima važnu obrazovnu funkciju sa svrhom postavljanja najviših standarda integriteta, odgovornog ponašanja i etičkog ponašanja u profesionalnim aktivnosti. Time profesionalna zajednica programskih inženjera definira opća načela koja definiranju  moralni karakter, donošenje važnih poslovnih odluka i uspostavljanje jasnih moralnih očekivanja za sve pripadnike zajenice.

Kodeks ponašanja skup je provedivih pravila koja služe za jasnu komunikaciju očekivanja i zahtjeva za rad zajednice/tima. Njime se jasno definiraju obaveze, prava, neprihvatljiva ponašanja te  odgovarajuće posljedice (za razliku od etičkog kodeksa). U ovom repozitoriju dan je jedan od široko prihvačenih kodeks ponašanja za rad u zajednici otvorenog koda.
>### Poboljšajte funkcioniranje tima:
>* definirajte načina na koji će rad biti podijeljen među članovima grupe
>* dogovorite kako će grupa međusobno komunicirati.
>* ne gubite vrijeme na dogovore na koji će grupa rješavati sporove primjenite standarde!
>* implicitno podrazmijevamo da će svi članovi grupe slijediti kodeks ponašanja.
 
>###  Prijava problema
>Najgore što se može dogoditi je da netko šuti kad postoje problemi. Postoji nekoliko stvari koje možete učiniti kako biste najbolje riješili sukobe i probleme:
>* Obratite mi se izravno [e-pošta](mailto:vlado.sruk@fer.hr) i  učinit ćemo sve što je u našoj moći da u punom povjerenju saznamo koje korake trebamo poduzeti kako bismo riješili problem.
>* Razgovarajte s vašim asistentom jer ima najbolji uvid u dinamiku tima. Zajedno ćete saznati kako riješiti sukob i kako izbjeći daljnje utjecanje u vašem radu.
>* Ako se osjećate ugodno neposredno razgovarajte o problemu. Manje incidente trebalo bi rješavati izravno. Odvojite vrijeme i privatno razgovarajte s pogođenim članom tima te vjerujte u iskrenost.

# 📝 Licenca
Važeča (1)
[![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

Ovaj repozitorij sadrži otvoreni obrazovni sadržaji (eng. Open Educational Resources)  i licenciran je prema pravilima Creative Commons licencije koja omogućava da preuzmete djelo, podijelite ga s drugima uz 
uvjet da navođenja autora, ne upotrebljavate ga u komercijalne svrhe te dijelite pod istim uvjetima [Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License HR][cc-by-nc-sa].
>
> ### Napomena:
>
> Svi paketi distribuiraju se pod vlastitim licencama.
> Svi upotrijebleni materijali  (slike, modeli, animacije, ...) distribuiraju se pod vlastitim licencama.

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: https://creativecommons.org/licenses/by-nc/4.0/deed.hr 
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg

Orginal [![cc0-1.0][cc0-1.0-shield]][cc0-1.0]
>
>COPYING: All the content within this repository is dedicated to the public domain under the CC0 1.0 Universal (CC0 1.0) Public Domain Dedication.
>
[![CC0-1.0][cc0-1.0-image]][cc0-1.0]

[cc0-1.0]: https://creativecommons.org/licenses/by/1.0/deed.en
[cc0-1.0-image]: https://licensebuttons.net/l/by/1.0/88x31.png
[cc0-1.0-shield]: https://img.shields.io/badge/License-CC0--1.0-lightgrey.svg

### Reference na licenciranje repozitorija

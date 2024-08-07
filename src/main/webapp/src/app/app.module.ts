import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ScoreComponent} from './score/score.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule, MatSortModule, MatTableModule} from "@angular/material";


@NgModule({
    declarations: [
        AppComponent,
        ScoreComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        MatTableModule,
        MatSortModule,
        MatCardModule,
        BrowserAnimationsModule,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }

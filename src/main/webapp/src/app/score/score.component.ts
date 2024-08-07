import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ScoreDataSource, ScoreService} from "./score.service";
import {MatSort} from "@angular/material";

@Component({
    selector: 'app-score',
    styleUrls: ['score.component.css'],
    templateUrl: './score.component.html',
    providers: [ScoreService]
})
export class ScoreComponent implements OnInit, AfterViewInit {
    error: any;
    dataSource: ScoreDataSource;
    displayedColumns: string[] = ['userId', 'score'];

    @ViewChild(MatSort) sort: MatSort;

    constructor(private scoreService: ScoreService) {}

    ngOnInit() {
        this.dataSource = new ScoreDataSource(this.scoreService);
        this.dataSource.loadScores();
    }

    ngAfterViewInit(): void {
        this.sort.sortChange.subscribe(() => this.loadScoresPage());
    }

    loadScoresPage() {
        this.dataSource.loadScores(this.sort.direction);
    }

}

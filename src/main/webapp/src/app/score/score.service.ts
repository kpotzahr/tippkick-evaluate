import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, Observable, of} from "rxjs";
import {catchError, finalize} from "rxjs/operators";

export interface Score {
    userId: number;
    score: number;
}

@Injectable()
export class ScoreService {
    scoreUrl = '/score';

    constructor(private http: HttpClient) { }

    findScores(sortOrder = 'desc', pageNumber = 0, pageSize = 100): Observable<Score[]> {
        return this.http.get<Score[]>(this.scoreUrl, {
            params: new HttpParams()
                .set('sortOrder', sortOrder)
                .set('pageNumber', pageNumber.toString())
                .set('pageSize', pageSize.toString())
        });
    }
}

export class ScoreDataSource implements DataSource<Score> {

    private scoreSubject = new BehaviorSubject<Score[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    constructor(private scoreService: ScoreService) {
    }

    connect(collectionViewer: CollectionViewer): Observable<Score[]> {
        return this.scoreSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.loadingSubject.complete();
        this.scoreSubject.complete();
    }

    loadScores(sortDirection = 'desc') {
        this.loadingSubject.next(true);
        this.scoreService.findScores(sortDirection)
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(scores => this.scoreSubject.next(scores));
    }
}
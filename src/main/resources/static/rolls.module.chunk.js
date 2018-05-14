webpackJsonp(["rolls.module"],{

/***/ "./src/app/app-utils/app-comparators.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (immutable) */ __webpack_exports__["a"] = compareColors;
/* unused harmony export rgbToHsl */
/* harmony export (immutable) */ __webpack_exports__["b"] = compareDates;
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_moment__ = __webpack_require__("./node_modules/moment/moment.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_moment___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_moment__);

/**
 * @returns numeric value of the resulting comparation
 * @description compares two hexademical color codes in format #ff11aa
 *
 * @param c1 color code only in hexademical format of the first element
 * @param c2 color code only in hexademical format of the second element
 */
function compareColors(c1, c2) {
    var a1 = c1.substr(1, c1.length);
    var a2 = c2.substr(1, c2.length);
    var base = 16;
    var r1 = parseInt(a1.substr(0, 2), base);
    var g1 = parseInt(a1.substr(2, 2), base);
    var b1 = parseInt(a1.substr(4, 2), base);
    var r2 = parseInt(a2.substr(0, 2), base);
    var g2 = parseInt(a2.substr(2, 2), base);
    var b2 = parseInt(a2.substr(4, 2), base);
    var hsl1 = rgbToHsl(r1, g1, b1);
    var hsl2 = rgbToHsl(r2, g2, b2);
    var result = hsl1[0] - hsl2[0];
    if (result != 0) {
        return result;
    }
    else if ((result = hsl1[1] - hsl2[1]) != 0) {
        return result;
    }
    else {
        result = hsl1[2] - hsl2[2];
    }
    return result;
}
/**
 * @returns color code in hsl format as array of numbers [hue, saturation, lightness]
 * @description transforms rgb color code to hsl
 *
 * @param r red color value between 0 and 255
 * @param g green color value between 0 and 255
 * @param b blue color value between 0 and 255
 */
function rgbToHsl(r, g, b) {
    r /= 255, g /= 255, b /= 255;
    var max = Math.max(r, g, b), min = Math.min(r, g, b);
    var h, s, l = (max + min) / 2;
    if (max == min) {
        h = s = 0;
    }
    else {
        var d = max - min;
        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
        switch (max) {
            case r:
                h = (g - b) / d + (g < b ? 6 : 0);
                break;
            case g:
                h = (b - r) / d + 2;
                break;
            case b:
                h = (r - g) / d + 4;
                break;
        }
        h /= 6;
    }
    return [(h * 100 + 0.5) | 0, ((s * 100 + 0.5) | 0), ((l * 100 + 0.5) | 0)];
}
function compareDates(d1, d2, format) {
    if (format === void 0) { format = 'DD-MM-YYYY'; }
    var m1 = __WEBPACK_IMPORTED_MODULE_0_moment__(d1, format);
    var m2 = __WEBPACK_IMPORTED_MODULE_0_moment__(d2, format);
    var result = m1.diff(m2, 'days');
    return result;
}


/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-check/roll-check.component.css":
/***/ (function(module, exports) {

module.exports = "::ng-deep .ng-value-container, ng-arrow-wrapper {            \n    padding: 0px !important;\n    margin: 0px !important;    \n}\n\n::ng-deep .ng-select .ng-select-container {\n    width: -webkit-fit-content !important;\n    width: -moz-fit-content !important;\n    width: fit-content !important;\n    height: -webkit-fit-content !important;\n    height: -moz-fit-content !important;\n    height: fit-content !important;\n    min-height: 0px;\n}\n\n::ng-deep .ng-dropdown-panel .ng-dropdown-panel-items .ng-option {\n    padding: 0;\n    text-align: left;\n}\n\n::ng-deep .ng-dropdown-panel .ng-dropdown-panel-items .ng-option.ng-option-selected,\n::ng-deep .ng-dropdown-panel .ng-dropdown-panel-items .ng-option:hover {\n    background-color: rgb(220, 239, 255);\n}\n\n::ng-deep .ng-dropdown-panel {\n    display: -webkit-box;\n    display: -ms-flexbox;\n    display: flex;\n}\n"

/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-check/roll-check.component.html":
/***/ (function(module, exports) {

module.exports = "<ng-select [(ngModel)]=\"selectedModel\" [items]=\"items\" [searchable]=\"false\" [clearable]=\"false\" bindLabel=\"label\"\n  appendTo=\"ng-select\" (change)=\"changeRollCheck($event)\">\n  <ng-template ng-label-tmp let-item=\"item\">\n    <span class=\"material-icons\" [ngClass]=\"item.clazz\">{{ item.label }}</span>\n  </ng-template>\n  <ng-template ng-option-tmp let-item=\"item\" let-index=\"index\" let-search=\"searchTerm\">\n    <b class=\"material-icons\" [ngClass]=\"item.clazz\" [ngOptionHighlight]=\"search\">{{item.label}}</b>\n  </ng-template>\n</ng-select>\n"

/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-check/roll-check.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollCheckComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__enums_check_status_enum__ = __webpack_require__("./src/app/modules/app-rolls/enums/check-status.enum.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var RollCheckComponent = /** @class */ (function () {
    function RollCheckComponent() {
        this.changeCheckStatus = new __WEBPACK_IMPORTED_MODULE_0__angular_core__["w" /* EventEmitter */]();
        this.items = [{
                label: 'check_box_outline_blank',
                value: 'NOT_CHECKED',
                clazz: 'text-secondary'
            },
            {
                label: 'check_box',
                value: 'CONFIRMED',
                clazz: 'text-success'
            },
            {
                label: 'indeterminate_check_box',
                value: 'NOT_CONFIRMED',
                clazz: 'text-danger'
            }
        ];
    }
    RollCheckComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.selectedModel = this.items
            .find(function (value, index, array) { return value.value === _this.rollCheck.rollLeftOverCheckStatus; });
    };
    RollCheckComponent.prototype.changeRollCheck = function (model) {
        var result = {};
        Object.assign(result, this.rollCheck);
        result.rollLeftOverCheckStatus = __WEBPACK_IMPORTED_MODULE_1__enums_check_status_enum__["a" /* CheckStatus */][model.value];
        this.changeCheckStatus.emit(result);
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["E" /* Input */])(),
        __metadata("design:type", Object)
    ], RollCheckComponent.prototype, "rollCheck", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Q" /* Output */])(),
        __metadata("design:type", Object)
    ], RollCheckComponent.prototype, "changeCheckStatus", void 0);
    RollCheckComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-roll-check',
            template: __webpack_require__("./src/app/modules/app-rolls/components/roll-check/roll-check.component.html"),
            styles: [__webpack_require__("./src/app/modules/app-rolls/components/roll-check/roll-check.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], RollCheckComponent);
    return RollCheckComponent;
}());



/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-operation-modal/roll-operation-modal.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-operation-modal/roll-operation-modal.component.html":
/***/ (function(module, exports) {

module.exports = "<div *ngIf=\"batch\">\n  <h6>\n    Текущий остаток: {{ batch.leftOverAmount }}\n  </h6>\n  <hr>\n</div>\n<form [formGroup]=\"form\">\n  <div class=\"form-group\">\n    <label for=\"operation-type\">Тип операции:</label>\n    <select class=\"form-control\" id=\"operation-type\" formControlName=\"operationType\" (change)=\"revalidateAmount()\">\n      <option value=\"MANUFACTURE\" selected>Производство</option>\n      <option value=\"USE\">Списание</option>\n    </select>\n  </div>\n  <div class=\"form-group\">\n    <label for=\"roll-amount\">Количество рулонов:</label>\n    <input type=\"number\" min=\"0\" step=\"1\" class=\"form-control\" id=\"roll-amount\" formControlName=\"rollAmount\" [ngClass]=\"{\n        'is-invalid': form.get('rollAmount').invalid && isTouched('rollAmount'),\n        'is-valid': form.get('rollAmount').valid && isTouched('rollAmount')\n      }\">\n    <div class=\"invalid-feedback\" *ngIf=\"form.get('rollAmount').invalid && isTouched('rollAmount')\">\n      <span *ngIf=\"form.get('rollAmount').errors['required']\">\n        Количество рулонов не может быть пустым!\n      </span>\n      <span *ngIf=\"form.get('rollAmount').errors['min']\">\n        Минимальное количество рулонов для операции: {{ MIN_ROLL_AMOUNT }}\n      </span>\n      <span *ngIf=\"form.get('rollAmount').errors['greaterThanLeftError']\">\n        При использовании количество рулонов не может быть больше чем текущий остаток: {{ batch.leftOverAmount }}\n      </span>\n      <span *ngIf=\"form.get('rollAmount').errors['notIntegerError']\">\n        Количество рулонов должно быть целым числом!\n      </span>\n    </div>\n  </div>\n</form>\n"

/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-operation-modal/roll-operation-modal.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollOperationModalComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__enums_roll_operation_type_enum__ = __webpack_require__("./src/app/modules/app-rolls/enums/roll-operation-type.enum.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__app_utils_app_validators__ = __webpack_require__("./src/app/app-utils/app-validators.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__ = __webpack_require__("./src/app/app-utils/app-date-utils.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var RollOperationModalComponent = /** @class */ (function () {
    function RollOperationModalComponent() {
        this.operationType = __WEBPACK_IMPORTED_MODULE_2__enums_roll_operation_type_enum__["a" /* RollOperationType */].MANUFACTURE;
        this.MIN_ROLL_AMOUNT = 1;
        this.btnClass = 'btn btn-outline-dark';
        this.submitPressed = false;
        this.actionButtons = [{
                text: 'Отмена',
                buttonClass: this.btnClass,
                onAction: function () { return true; }
            },
            {
                text: 'Произвести операцию',
                buttonClass: this.btnClass,
                onAction: this.onSubmit.bind(this)
            }
        ];
    }
    RollOperationModalComponent.prototype.ngOnInit = function () {
        this.form = new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["b" /* FormGroup */]({
            operationType: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */]({
                value: this.operationType,
                disabled: !this.batch
            }),
            rollAmount: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](undefined, [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].min(this.MIN_ROLL_AMOUNT), this.validateAmount.bind(this), __WEBPACK_IMPORTED_MODULE_3__app_utils_app_validators__["a" /* integerValidator */]])
        });
    };
    RollOperationModalComponent.prototype.dialogInit = function (reference, options) {
        this.options = options;
        this.batch = options.data.batch;
        this.rollTypeId = options.data.rollTypeId;
        this.manufacturedDate = options.data.manufacturedDate;
    };
    RollOperationModalComponent.prototype.onSubmit = function () {
        if (this.form.invalid) {
            this.submitPressed = true;
            var reject = Promise.reject('invalid');
            this.options.data.operation(reject);
            return reject;
        }
        var rollOperation = {
            operationDate: Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["b" /* formatDate */])(Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["g" /* midnightDate */])()),
            operationType: this.form.get('operationType').value,
            manufacturedDate: Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["b" /* formatDate */])(this.manufacturedDate),
            rollTypeId: this.rollTypeId,
            rollAmount: this.form.get('rollAmount').value
        };
        var resolve = Promise.resolve(rollOperation);
        this.options.data.operation(resolve);
        return resolve;
    };
    RollOperationModalComponent.prototype.validateAmount = function (control) {
        if (this.batch && control.value > this.batch.leftOverAmount) {
            if (this.form.value.operationType == __WEBPACK_IMPORTED_MODULE_2__enums_roll_operation_type_enum__["a" /* RollOperationType */].USE) {
                return {
                    'greaterThanLeftError': true
                };
            }
        }
        return null;
    };
    RollOperationModalComponent.prototype.revalidateAmount = function () {
        this.form.get('rollAmount').updateValueAndValidity();
    };
    RollOperationModalComponent.prototype.isTouched = function (controlName) {
        return this.form.get(controlName).touched || this.submitPressed;
    };
    RollOperationModalComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-roll-operation-modal',
            template: __webpack_require__("./src/app/modules/app-rolls/components/roll-operation-modal/roll-operation-modal.component.html"),
            styles: [__webpack_require__("./src/app/modules/app-rolls/components/roll-operation-modal/roll-operation-modal.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], RollOperationModalComponent);
    return RollOperationModalComponent;
}());



/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-operations-page/roll-operations-page.component.css":
/***/ (function(module, exports) {

module.exports = "\n"

/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-operations-page/roll-operations-page.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid mt-2\">\n  <form [formGroup]=\"form\">\n    <div class=\"form-row\">\n      <div class=\"col-md-3 offset-md-3 pl-2 pr-2 pt-1 pb-1 pt-md-0 pb-md-0\">\n        <div class=\"input-group\">\n          <div class=\"input-group-prepend\">\n            <div class=\"input-group-text\">от</div>\n          </div>\n          <input type=\"date\" class=\"form-control\" id=\"fromDate\" formControlName=\"fromDate\" [ngClass]=\"{\n            'is-invalid': form.get('fromDate').invalid,\n            'is-valid': form.get('fromDate').valid && form.get('fromDate').touched\n          }\">\n          <div class=\"invalid-feedback\" *ngIf=\"form.get('fromDate').invalid\">\n            <span *ngIf=\"form.get('fromDate').errors['required']\">Не может быть пустым!</span>\n            <span *ngIf=\"form.get('fromDate').errors['biggerThenToDate']\">Дата начала должна быть меньше даты конца!</span>\n          </div>\n        </div>\n      </div>\n      <div class=\"col-md-3 pl-2 pr-2 pt-1 pb-1 pt-md-0 pb-md-0\">\n        <div class=\"input-group\">\n          <div class=\"input-group-prepend\">\n            <div class=\"input-group-text\">до</div>\n          </div>\n          <input type=\"date\" class=\"form-control\" id=\"toDate\" formControlName=\"toDate\" [ngClass]=\"{\n            'is-invalid': form.get('toDate').invalid,\n            'is-valid': form.get('toDate').valid && form.get('toDate').touched\n          }\">\n          <div class=\"invalid-feedback\" *ngIf=\"form.get('toDate').invalid\">\n            <span *ngIf=\"form.get('toDate').errors['required']\">Не может быть пустым!</span>\n            <span *ngIf=\"form.get('toDate').errors['smallerThenFromDate']\">Дата конца должна быть больше даты начала!</span>\n          </div>\n        </div>\n      </div>\n\n      <div class=\"col-md-1 pl-2 pr-2 pt-1 pb-1 pt-md-0 pb-md-0\">\n        <button type=\"submit\" class=\"btn btn-outline-dark\" [disabled]=\"form.invalid\" (click)=\"onSubmit()\">Показать</button>\n      </div>\n    </div>\n  </form>\n</div>\n<div class=\"container-fluid mt-2\">\n  <div class=\"table-responsive\">\n    <table class=\"table table-bordered table-striped\" *ngIf=\"showOperations()\">\n      <thead>\n        <tr class=\"table-header\">\n          <th scope=\"col\">Тип операции</th>\n          <th scope=\"col\">Дата операции</th>\n          <th scope=\"col\">Дата производства</th>\n          <th scope=\"col\">Количество</th>\n        </tr>\n      </thead>\n      <tbody>\n        <tr *ngFor=\"let operation of rollOperations\">\n          <td>{{ getOperationType(operation.operationType) }}</td>\n          <td>{{ operation.operationDate }}</td>\n          <td>{{ operation.manufacturedDate }}</td>\n          <td>{{ operation.rollAmount }}</td>\n        </tr>\n      </tbody>\n    </table>\n  </div>\n</div>\n<div *ngIf=\"!showOperations()\">\n  <hr>\n  <h2 class=\"d-flex justify-content-center\">\n    За данный период нет операций\n  </h2>\n</div>\n"

/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-operations-page/roll-operations-page.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollOperationsPageComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_ngx_modal_dialog__ = __webpack_require__("./node_modules/ngx-modal-dialog/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__services_rolls_service__ = __webpack_require__("./src/app/modules/app-rolls/services/rolls.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__enums_roll_operation_type_enum__ = __webpack_require__("./src/app/modules/app-rolls/enums/roll-operation-type.enum.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__app_shared_services_app_modal_service__ = __webpack_require__("./src/app/modules/app-shared/services/app-modal.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__app_utils_app_date_utils__ = __webpack_require__("./src/app/app-utils/app-date-utils.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__app_utils_app_comparators__ = __webpack_require__("./src/app/app-utils/app-comparators.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var RollOperationsPageComponent = /** @class */ (function () {
    function RollOperationsPageComponent(route, router, rollsService, ngxModalService, viewRef, appModalService) {
        this.route = route;
        this.router = router;
        this.rollsService = rollsService;
        this.ngxModalService = ngxModalService;
        this.viewRef = viewRef;
        this.appModalService = appModalService;
        this.queryParams = Object.assign({}, this.route.snapshot.queryParams);
        this.rollTypeId = this.queryParams['roll_type_id'];
    }
    RollOperationsPageComponent.prototype.ngOnInit = function () {
        this.fetchData();
        this.form = new __WEBPACK_IMPORTED_MODULE_2__angular_forms__["b" /* FormGroup */]({
            fromDate: new __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormControl */](Object(__WEBPACK_IMPORTED_MODULE_7__app_utils_app_date_utils__["d" /* formatDateServerToBrowser */])(this.fromDateValue), [__WEBPACK_IMPORTED_MODULE_2__angular_forms__["g" /* Validators */].required, this.fromDateSmallerValidator.bind(this)]),
            toDate: new __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormControl */](Object(__WEBPACK_IMPORTED_MODULE_7__app_utils_app_date_utils__["d" /* formatDateServerToBrowser */])(this.toDateValue), [__WEBPACK_IMPORTED_MODULE_2__angular_forms__["g" /* Validators */].required, this.toDateBiggerValidator.bind(this)])
        });
    };
    RollOperationsPageComponent.prototype.fetchData = function () {
        var _this = this;
        this.fromDateValue = this.queryParams['from'];
        this.toDateValue = this.queryParams['to'];
        this.rollsService.getRollOperations(this.rollTypeId, this.fromDateValue, this.toDateValue)
            .subscribe(function (data) {
            _this.rollOperations = data.sort(function (a, b) {
                return Object(__WEBPACK_IMPORTED_MODULE_8__app_utils_app_comparators__["b" /* compareDates */])(a.manufacturedDate, b.manufacturedDate);
            });
        }, function (error) {
            _this.rollOperations = undefined;
            _this.appModalService.openHttpErrorModal(_this.ngxModalService, _this.viewRef, error);
        });
    };
    RollOperationsPageComponent.prototype.getOperationType = function (operationType) {
        return operationType == __WEBPACK_IMPORTED_MODULE_5__enums_roll_operation_type_enum__["a" /* RollOperationType */].USE ? 'Списание' : 'Производство';
    };
    RollOperationsPageComponent.prototype.showOperations = function () {
        if (!this.rollOperations)
            return true;
        return this.rollOperations.length != 0;
    };
    RollOperationsPageComponent.prototype.onSubmit = function () {
        console.log(this.form);
        this.queryParams['from'] = Object(__WEBPACK_IMPORTED_MODULE_7__app_utils_app_date_utils__["c" /* formatDateBrowserToServer */])(this.form.value.fromDate);
        this.queryParams['to'] = Object(__WEBPACK_IMPORTED_MODULE_7__app_utils_app_date_utils__["c" /* formatDateBrowserToServer */])(this.form.value.toDate);
        this.router.navigate([], {
            relativeTo: this.route,
            queryParams: this.queryParams,
            replaceUrl: true
        });
        this.fetchData();
    };
    RollOperationsPageComponent.prototype.fromDateSmallerValidator = function (control) {
        if (control.value && this.form) {
            if (Object(__WEBPACK_IMPORTED_MODULE_8__app_utils_app_comparators__["b" /* compareDates */])(control.value, this.form.get('toDate').value, 'YYYY-MM-DD') > 0) {
                return {
                    'biggerThenToDate': true
                };
            }
        }
        return null;
    };
    RollOperationsPageComponent.prototype.toDateBiggerValidator = function (control) {
        if (control.value && this.form) {
            if (Object(__WEBPACK_IMPORTED_MODULE_8__app_utils_app_comparators__["b" /* compareDates */])(control.value, this.form.get('fromDate').value, 'YYYY-MM-DD') < 0) {
                return {
                    'smallerThenFromDate': true
                };
            }
        }
        return null;
    };
    RollOperationsPageComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-roll-operations-page',
            template: __webpack_require__("./src/app/modules/app-rolls/components/roll-operations-page/roll-operations-page.component.html"),
            styles: [__webpack_require__("./src/app/modules/app-rolls/components/roll-operations-page/roll-operations-page.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */],
            __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* Router */],
            __WEBPACK_IMPORTED_MODULE_4__services_rolls_service__["a" /* RollsService */],
            __WEBPACK_IMPORTED_MODULE_3_ngx_modal_dialog__["b" /* ModalDialogService */],
            __WEBPACK_IMPORTED_MODULE_0__angular_core__["_13" /* ViewContainerRef */],
            __WEBPACK_IMPORTED_MODULE_6__app_shared_services_app_modal_service__["a" /* AppModalService */]])
    ], RollOperationsPageComponent);
    return RollOperationsPageComponent;
}());



/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-type-modal/roll-type-modal.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-type-modal/roll-type-modal.component.html":
/***/ (function(module, exports) {

module.exports = "<form [formGroup]=\"form\">\n  <div class=\"row\">\n    <div class=\"col-md-6\">\n      <label for=\"roll-name\" class=\"col-form-label\">Примечание:</label>\n      <input type=\"text\" [maxlength]=\"MAX_NOTE_LENGTH\" class=\"form-control\" id=\"roll-name\" formControlName=\"note\" [ngClass]=\"{\n          'is-invalid': form.get('note').invalid && isTouched('note'),\n          'is-valid': form.get('note').valid && isTouched('note')\n        }\">\n      <div class=\"invalid-feedback\">\n        Максимальная длина {{ MAX_NOTE_LENGTH }}\n      </div>\n    </div>\n    <div class=\"col-md-4\">\n      <label for=\"roll-thickness\" class=\"col-form-label\">Толщина:</label>\n      <input type=\"number\" step=\"0.1\" class=\"form-control\" id=\"roll-thickness\" formControlName=\"thickness\" [ngClass]=\"{\n          'is-invalid': form.get('thickness').invalid && isTouched('thickness'),\n          'is-valid': form.get('thickness').valid && isTouched('thickness')\n        }\">\n      <div class=\"invalid-feedback\">\n        Толщина должна быть больше {{ MIN_THICKNESS }}\n      </div>\n    </div>\n    <div class=\"col-md-2\">\n      <label for=\"roll-color\" class=\"col-form-label\">Цвет:</label>\n      <input type=\"button\" class=\"form-control\" id=\"roll-color\" formControlName=\"colorCode\" [(colorPicker)]=\"colorCode\" [value]=\"colorCode\"\n        [style.background]=\"colorCode\" [style.color]=\"colorCode\" [cpOutputFormat]=\"'hex'\" [cpPosition]=\"'left'\" [cpOKButton]=\"true\"\n        [cpCancelButton]=\"true\" [cpPresetColors]=\"presetColors\">\n    </div>\n  </div>\n  <div class=\"row\">\n    <div class=\"col-md-12\">\n      <label for=\"roll-min-weight\" class=\"col-form-label\">Вес:</label>\n    </div>\n  </div>\n  <div class=\"row\">\n    <div class=\"col-md-6\">\n      <div class=\"input-group\">\n        <div class=\"input-group-prepend\">\n          <span class=\"input-group-text\">от:</span>\n        </div>\n        <input (input)=\"revalidateMaxWeight()\" type=\"number\" step=\"1\" class=\"form-control\" id=\"roll-min-weight\" formControlName=\"minWeight\"\n          [ngClass]=\"{\n        'is-invalid': form.get('minWeight').invalid && isTouched('minWeight'),\n        'is-valid': form.get('minWeight').valid && isTouched('minWeight')\n        }\">\n        <div class=\"invalid-feedback\" *ngIf=\"form.get('minWeight').invalid && isTouched('minWeight')\">\n          <span *ngIf=\"form.get('minWeight').errors['min']\">Вес должен быть больше {{ MIN_WEIGHT }}</span>\n          <span *ngIf=\"form.get('minWeight').errors['required']\">Вес не может быть пустым!</span>\n          <span *ngIf=\"form.get('minWeight').errors['greaterThenMax']\">Минимальное значение должно быть меньше максимального!</span>\n        </div>\n      </div>\n    </div>\n    <div class=\"col-md-6\">\n      <div class=\"input-group\">\n        <div class=\"input-group-prepend\">\n          <span class=\"input-group-text\">до:</span>\n        </div>\n        <input (input)=\"revalidateMinWeight()\" type=\"number\" step=\"1\" class=\"form-control\" id=\"roll-max-weight\" formControlName=\"maxWeight\"\n          [ngClass]=\"{\n        'is-invalid': form.get('maxWeight').invalid && isTouched('maxWeight'),\n        'is-valid': form.get('maxWeight').valid && isTouched('maxWeight')\n        }\">\n        <div class=\"invalid-feedback\" *ngIf=\"form.get('maxWeight').invalid && isTouched('maxWeight')\">\n          <span *ngIf=\"form.get('maxWeight').errors['min']\">Вес должен быть больше {{ MIN_WEIGHT }}</span>\n          <span *ngIf=\"form.get('maxWeight').errors['required']\">Вес не может быть пустым!</span>\n          <span *ngIf=\"form.get('maxWeight').errors['smallerThenMin']\">Максимальное значение должно быть больше минимального!</span>\n        </div>\n      </div>\n    </div>\n  </div>\n</form>\n"

/***/ }),

/***/ "./src/app/modules/app-rolls/components/roll-type-modal/roll-type-modal.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollTypeModalComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_utils_app_preset_colors__ = __webpack_require__("./src/app/app-utils/app-preset-colors.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var RollTypeModalComponent = /** @class */ (function () {
    function RollTypeModalComponent() {
        this.presetColors = __WEBPACK_IMPORTED_MODULE_2__app_utils_app_preset_colors__["a" /* default */];
        this.MIN_WEIGHT = 0.1;
        this.MIN_THICKNESS = 0.1;
        this.MAX_NOTE_LENGTH = 20;
        this.submitPressed = false;
        this.btnClass = 'btn btn-outline-dark';
        this.actionButtons = [{
                text: 'Отмена',
                buttonClass: this.btnClass,
                onAction: function () { return true; }
            },
            {
                text: 'Сохранить',
                buttonClass: this.btnClass,
                onAction: this.onSubmit.bind(this)
            }
        ];
    }
    RollTypeModalComponent.prototype.ngOnInit = function () {
        this.colorCode = this.rollType ? this.rollType.colorCode : __WEBPACK_IMPORTED_MODULE_2__app_utils_app_preset_colors__["a" /* default */][0];
        this.form = new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["b" /* FormGroup */]({
            note: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](this.rollType ? this.rollType.note : '', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].maxLength(this.MAX_NOTE_LENGTH)),
            colorCode: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](this.colorCode),
            thickness: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](this.rollType ? this.rollType.thickness : undefined, [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].min(this.MIN_THICKNESS)]),
            minWeight: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](this.rollType ? this.rollType.minWeight : undefined, [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].min(this.MIN_WEIGHT), this.validateMinWeight.bind(this)]),
            maxWeight: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](this.rollType ? this.rollType.maxWeight : undefined, [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].min(this.MIN_WEIGHT), this.validateMaxWeight.bind(this)])
        });
    };
    RollTypeModalComponent.prototype.dialogInit = function (reference, options) {
        this.options = options;
        this.rollType = options.data.rollType;
    };
    ;
    RollTypeModalComponent.prototype.onSubmit = function () {
        if (this.form.invalid) {
            this.submitPressed = true;
            var reject = Promise.reject('invalid');
            this.options.data.operation(reject);
            return reject;
        }
        var type = {
            id: undefined,
            note: this.form.value.note,
            colorCode: this.colorCode,
            thickness: this.form.value.thickness,
            minWeight: this.form.value.minWeight,
            maxWeight: this.form.value.maxWeight
        };
        var resolve = Promise.resolve(type);
        this.options.data.operation(resolve);
        return resolve;
    };
    RollTypeModalComponent.prototype.validateMinWeight = function (control) {
        if (control.value && this.form) {
            if (control.value > this.form.get('maxWeight').value) {
                return {
                    'greaterThenMax': true
                };
            }
        }
        return null;
    };
    RollTypeModalComponent.prototype.validateMaxWeight = function (control) {
        if (control.value && this.form) {
            if (control.value < this.form.get('minWeight').value) {
                return {
                    'smallerThenMin': true
                };
            }
        }
        return null;
    };
    RollTypeModalComponent.prototype.revalidateMinWeight = function () {
        this.form.get('minWeight').updateValueAndValidity();
    };
    RollTypeModalComponent.prototype.revalidateMaxWeight = function () {
        this.form.get('maxWeight').updateValueAndValidity();
    };
    RollTypeModalComponent.prototype.isTouched = function (controlName) {
        return this.form.get(controlName).touched || this.submitPressed;
    };
    RollTypeModalComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-roll-type-modal',
            template: __webpack_require__("./src/app/modules/app-rolls/components/roll-type-modal/roll-type-modal.component.html"),
            styles: [__webpack_require__("./src/app/modules/app-rolls/components/roll-type-modal/roll-type-modal.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], RollTypeModalComponent);
    return RollTypeModalComponent;
}());



/***/ }),

/***/ "./src/app/modules/app-rolls/components/rolls-page/rolls-page.component.css":
/***/ (function(module, exports) {

module.exports = ".container-fluid {\n  margin-top: 0.5rem;\n}\n\nbutton {\n  margin-bottom: 0.5rem;\n}\n\n.roll-ready {\n    color: #3c39cc;\n}\n\n.roll-not-ready {\n  color: #6d6d6d;\n}\n\nth, td {\n  white-space: nowrap;\n}\n\napp-roll-check {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-pack: center;\n      -ms-flex-pack: center;\n          justify-content: center;\n}\n\ntable {\n  margin-bottom: 10%;\n}\n\n::ng-deep .dropdown-item {\n  font-size: 0.9rem;\n  padding: .1rem 1rem;\n}\n"

/***/ }),

/***/ "./src/app/modules/app-rolls/components/rolls-page/rolls-page.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\n  <button class=\"btn btn-outline-dark btn-sm\" (click)=\"openAddRollTypeModal()\">\n    <span class=\"material-icons\">add</span>\n  </button>\n  <button type=\"button\" class=\"btn btn-outline-dark btn-sm\" (click)=\"showPreviousPeriod()\">\n    <span class=\"material-icons\">arrow_back</span>\n    Предыдущий период\n  </button>\n  <button *ngIf=\"isBeforePreviousPeriod()\" class=\"btn btn-outline-dark btn-sm\" (click)=\"showNextPeriod()\">\n    Следующий период\n    <span class=\"material-icons\">arrow_forward</span>\n  </button>\n  <button *ngIf=\"isPreviousPeriod()\" class=\"btn btn-outline-dark btn-sm\" (click)=\"showCurrentPeriod()\">\n    Текущий период\n    <span class=\"material-icons\">timelapse</span>\n  </button>\n  <div class=\"table-responsive rolls-table\">\n    <table class=\"table table-bordered table-striped table-sm\">\n      <thead>\n        <tr class=\"table-header\">\n          <th scope=\"col\" colspan=\"4\"></th>\n          <th *ngFor=\"let monthYear of dateHeader\" scope=\"col\" [colSpan]=\"monthYearMap.get(monthYear) + 2/dateHeader.length\">\n            <small>{{ monthYear }}</small>\n          </th>\n          <th scope=\"col\" *ngIf=\"!isPreviousPeriod()\">\n            <button type=\"button\" class=\"btn btn-outline-dark btn-sm p-0 pl-2 pr-2 m-0\" (click)=\"submitRollChecks()\">\n              <span class=\"material-icons\">save</span>\n            </button>\n          </th>\n        </tr>\n        <tr class=\"table-header\">\n          <th scope=\"col\" class=\"row-name\">Примечание</th>\n          <th scope=\"col\">Толщина</th>\n          <th scope=\"col\">Вес</th>\n          <th scope=\"col\">Цвет</th>\n          <th scope=\"col\">Остаток</th>\n          <th scope=\"col\" *ngFor=\"let day of daysHeader\">\n            {{ day.getDate() }}\n          </th>\n          <th scope=\"col\">Всего</th>\n          <th scope=\"col\" *ngIf=\"!isPreviousPeriod()\">Проверка</th>\n        </tr>\n      </thead>\n      <tbody>\n        <tr *ngFor=\"\n        let rollInfo of sortByColorThicknessRollId(rollsInfo)\" [contextMenu]=\"rollsMenu\" [contextMenuSubject]=\"rollInfo.rollType\">\n          <th scope=\"row\" class=\"row-name\">\n            {{ rollInfo.rollType.note }}\n          </th>\n          <th scope=\"row\" class=\"row-name\">\n            {{ rollInfo.rollType.thickness }}\n          </th>\n          <th scope=\"row\" class=\"row-name\">\n            {{ getWeight(rollInfo.rollType) }}\n          </th>\n          <th scope=\"row\" class=\"row-name\">\n            <span class=\"filled-circle\" [ngStyle]=\"{ 'background-color': rollInfo.rollType.colorCode}\"></span>\n          </th>\n          <td>\n            {{ rollInfo.restRollLeftover.amount }}\n          </td>\n          <td *ngFor=\"let batch of getBatches(rollInfo.rollBatches); index as i\" (click)=\"openCreateRollOperationModal(batch, i, rollInfo.rollType.id)\">\n            <div [ngClass]=\"{\n              'roll-ready': isReady(batch),\n              'roll-not-ready': !isReady(batch)\n            }\">\n              {{ getBatch(batch) }}\n            </div>\n          </td>\n          <td>\n            {{ rollInfo.totalRollLeftover.amount }}\n          </td>\n          <td *ngIf=\"!isPreviousPeriod()\">\n            <app-roll-check [rollCheck]=\"rollInfo.rollCheck\" (changeCheckStatus)=\"onChangeRollCheck($event)\"></app-roll-check>\n          </td>\n        </tr>\n      </tbody>\n    </table>\n  </div>\n</div>\n\n<context-menu #rollsMenu>\n  <ng-template contextMenuItem let-item (execute)=\"openRollOperationsPage($event.item)\">\n    Список операций\n  </ng-template>\n  <ng-template contextMenuItem divider=\"true\"></ng-template>\n  <ng-template contextMenuItem (execute)=\"openEditRollTypeModal($event.item)\">\n    Редактировать рулон\n  </ng-template>\n  <ng-template contextMenuItem divider=\"true\"></ng-template>\n  <ng-template contextMenuItem let-item (execute)=\"openDeleteRollTypeModal($event.item)\">\n    Удалить рулон\n  </ng-template>\n</context-menu>\n"

/***/ }),

/***/ "./src/app/modules/app-rolls/components/rolls-page/rolls-page.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollsPageComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_ngx_contextmenu__ = __webpack_require__("./node_modules/ngx-contextmenu/lib/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_ngx_modal_dialog__ = __webpack_require__("./node_modules/ngx-modal-dialog/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_moment__ = __webpack_require__("./node_modules/moment/moment.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_moment___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_moment__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__ = __webpack_require__("./src/app/app-utils/app-date-utils.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__services_rolls_service__ = __webpack_require__("./src/app/modules/app-rolls/services/rolls.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__app_utils_app_comparators__ = __webpack_require__("./src/app/app-utils/app-comparators.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__roll_type_modal_roll_type_modal_component__ = __webpack_require__("./src/app/modules/app-rolls/components/roll-type-modal/roll-type-modal.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__roll_operation_modal_roll_operation_modal_component__ = __webpack_require__("./src/app/modules/app-rolls/components/roll-operation-modal/roll-operation-modal.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__app_shared_services_app_modal_service__ = __webpack_require__("./src/app/modules/app-shared/services/app-modal.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__app_shared_components_simple_confirm_modal_simple_confirm_modal_component__ = __webpack_require__("./src/app/modules/app-shared/components/simple-confirm-modal/simple-confirm-modal.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};












var RollsPageComponent = /** @class */ (function () {
    function RollsPageComponent(rollsService, ngxModalService, viewRef, router, route, appModalService) {
        this.rollsService = rollsService;
        this.ngxModalService = ngxModalService;
        this.viewRef = viewRef;
        this.router = router;
        this.route = route;
        this.appModalService = appModalService;
        this.rollsInfo = [];
        this.daysInTable = 30;
        this.toDate = Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["g" /* midnightDate */])();
        this.rollChecks = new Map();
    }
    RollsPageComponent.prototype.ngOnInit = function () {
        this.showCurrentPeriod();
    };
    RollsPageComponent.prototype.initTableHeader = function (dateTo) {
        this.toDate = dateTo;
        this.restDate = Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["h" /* substructDays */])(dateTo, this.daysInTable);
        this.fromDate = Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["h" /* substructDays */])(dateTo, this.daysInTable - 1);
        this.daysHeader = [];
        this.monthYearMap = new Map();
        for (var i = 0; i < this.daysInTable; i++) {
            var substructedDate = Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["a" /* addDays */])(this.fromDate, i);
            this.daysHeader.push(substructedDate);
            var monthYear = __WEBPACK_IMPORTED_MODULE_4_moment__(substructedDate).locale('ru').format('MMM YY');
            this.monthYearMap.set(monthYear, this.monthYearMap.has(monthYear) ? this.monthYearMap.get(monthYear) + 1 : 1);
        }
        this.dateHeader = Array.from(this.monthYearMap.keys());
    };
    RollsPageComponent.prototype.fetchTableData = function () {
        var _this = this;
        if (this.isPreviousPeriod()) {
            this.rollsService.getRollsInfoWithoutCheck(this.restDate, this.fromDate, this.toDate)
                .subscribe(function (data) {
                _this.rollsInfo = data;
            }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalService, _this.viewRef, error); });
        }
        else {
            this.rollsService.getRollsInfo(this.restDate, this.fromDate, this.toDate)
                .subscribe(function (data) {
                _this.rollsInfo = data;
            }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalService, _this.viewRef, error); });
        }
    };
    RollsPageComponent.prototype.showPreviousPeriod = function () {
        this.initTableHeader(Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["h" /* substructDays */])(this.toDate, this.daysInTable));
        this.fetchTableData();
    };
    RollsPageComponent.prototype.showNextPeriod = function () {
        this.initTableHeader(Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["a" /* addDays */])(this.toDate, this.daysInTable));
        this.fetchTableData();
    };
    RollsPageComponent.prototype.isPreviousPeriod = function () {
        var accuracy = 10000;
        return this.toDate.getTime() + accuracy < Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["g" /* midnightDate */])().getTime();
    };
    RollsPageComponent.prototype.isBeforePreviousPeriod = function () {
        var accuracy = 10000;
        return this.toDate.getTime() + accuracy < Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["h" /* substructDays */])(Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["g" /* midnightDate */])(), this.daysInTable).getTime();
    };
    RollsPageComponent.prototype.showCurrentPeriod = function () {
        this.initTableHeader(Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["g" /* midnightDate */])());
        this.fetchTableData();
    };
    RollsPageComponent.prototype.getBatch = function (rollBatch) {
        if (rollBatch)
            return rollBatch.leftOverAmount;
        else
            return '';
    };
    RollsPageComponent.prototype.getBatches = function (rollBatches) {
        var _this = this;
        var result = new Array(this.daysInTable);
        rollBatches.forEach(function (item) { return result[Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["f" /* getIndex */])(Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["g" /* midnightDate */])(item.dateManufactured), result.length, (24 * 60 * 60 * 1000), _this.toDate)] = item; });
        return result;
    };
    RollsPageComponent.prototype.getWeight = function (rollType) {
        return rollType.minWeight == rollType.maxWeight ? rollType.minWeight : rollType.minWeight + "\u2013" + rollType.maxWeight;
    };
    RollsPageComponent.prototype.sortByColorThicknessRollId = function (rollsInfo) {
        return rollsInfo.sort(function (a, b) {
            var colorSortValue = Object(__WEBPACK_IMPORTED_MODULE_7__app_utils_app_comparators__["a" /* compareColors */])(a.rollType.colorCode, b.rollType.colorCode);
            var thicknessSort = a.rollType.thickness - b.rollType.thickness;
            return colorSortValue != 0 ? colorSortValue :
                thicknessSort != 0 ? thicknessSort : a.rollType.id - b.rollType.id;
        });
    };
    RollsPageComponent.prototype.openAddRollTypeModal = function () {
        var _this = this;
        var operation = function (result) {
            result
                .then(function (resolve) {
                _this.rollsService.postRollType(resolve, _this.daysInTable, _this.restDate, _this.toDate)
                    .subscribe(function (rollInfo) {
                    _this.rollsInfo.push(rollInfo);
                }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalService, _this.viewRef, error); });
            }, function (reject) { });
        };
        var modalOptions = {
            title: 'Новый рулон',
            childComponent: __WEBPACK_IMPORTED_MODULE_8__roll_type_modal_roll_type_modal_component__["a" /* RollTypeModalComponent */],
            data: {
                operation: operation.bind(this)
            }
        };
        this.ngxModalService.openDialog(this.viewRef, modalOptions);
    };
    RollsPageComponent.prototype.openEditRollTypeModal = function (rollType) {
        var _this = this;
        var operation = function (result) {
            result
                .then(function (resolve) {
                resolve.id = rollType.id;
                _this.rollsService.putRollType(resolve)
                    .subscribe(function (x) {
                    rollType.id = x.id;
                    rollType.note = x.note;
                    rollType.colorCode = x.colorCode;
                    rollType.thickness = x.thickness;
                    rollType.minWeight = x.minWeight;
                    rollType.maxWeight = x.maxWeight;
                }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalService, _this.viewRef, error); });
            }, function (reject) { });
        };
        var modalOptions = {
            title: 'Редактирование рулона',
            childComponent: __WEBPACK_IMPORTED_MODULE_8__roll_type_modal_roll_type_modal_component__["a" /* RollTypeModalComponent */],
            data: {
                rollType: rollType,
                operation: operation.bind(this)
            }
        };
        this.ngxModalService.openDialog(this.viewRef, modalOptions);
    };
    RollsPageComponent.prototype.openCreateRollOperationModal = function (batch, index, rollTypeId) {
        var _this = this;
        var operation = function (result) {
            result
                .then(function (resolve) {
                _this.rollsService.postRollOperation(resolve).subscribe(function (data) {
                    _this.fetchTableData();
                }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalService, _this.viewRef, error); });
            }, function (reject) { });
        };
        var modalOptions = {
            title: 'Операция над рулонами',
            childComponent: __WEBPACK_IMPORTED_MODULE_9__roll_operation_modal_roll_operation_modal_component__["a" /* RollOperationModalComponent */],
            data: {
                batch: batch,
                rollTypeId: rollTypeId,
                manufacturedDate: this.daysHeader[index],
                operation: operation.bind(this)
            }
        };
        this.ngxModalService.openDialog(this.viewRef, modalOptions);
    };
    RollsPageComponent.prototype.isReady = function (batch) {
        if (!batch)
            return false;
        return batch.readyToUse;
    };
    RollsPageComponent.prototype.onChangeRollCheck = function (rollCheck) {
        this.rollChecks.set(rollCheck.id, rollCheck);
    };
    RollsPageComponent.prototype.submitRollChecks = function () {
        var _this = this;
        this.rollsService.putRollChecks(Array.from(this.rollChecks.values()))
            .subscribe(function (data) {
            if (data.length != 0) {
                _this.fetchTableData();
                _this.rollChecks.clear();
            }
        }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalService, _this.viewRef, error); });
    };
    RollsPageComponent.prototype.openRollOperationsPage = function (item) {
        this.router.navigate(['operations'], {
            relativeTo: this.route,
            queryParams: {
                'roll_type_id': item.id,
                'from': Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(this.fromDate),
                'to': Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(this.toDate)
            }
        });
    };
    RollsPageComponent.prototype.openDeleteRollTypeModal = function (item) {
        var _this = this;
        var buttonClass = 'btn btn-outline-dark';
        var modalOptions = {
            title: 'Подтвердите удаление рулона',
            childComponent: __WEBPACK_IMPORTED_MODULE_11__app_shared_components_simple_confirm_modal_simple_confirm_modal_component__["a" /* SimpleConfirmModalComponent */],
            actionButtons: [{
                    text: 'Отменить',
                    buttonClass: buttonClass,
                    onAction: function () { return true; }
                },
                {
                    text: 'Удалить',
                    buttonClass: buttonClass,
                    onAction: function () {
                        _this.rollsService.deleteRollType(item.id)
                            .subscribe(function (data) {
                            _this.rollsInfo = _this.rollsInfo.filter(function (value, index, array) { return value.rollType.id != item.id; });
                        }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalService, _this.viewRef, error); });
                        return true;
                    }
                }
            ]
        };
        this.ngxModalService.openDialog(this.viewRef, modalOptions);
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_11" /* ViewChild */])(__WEBPACK_IMPORTED_MODULE_2_ngx_contextmenu__["a" /* ContextMenuComponent */]),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_2_ngx_contextmenu__["a" /* ContextMenuComponent */])
    ], RollsPageComponent.prototype, "rollsMenu", void 0);
    RollsPageComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-rolls-page',
            template: __webpack_require__("./src/app/modules/app-rolls/components/rolls-page/rolls-page.component.html"),
            styles: [__webpack_require__("./src/app/modules/app-rolls/components/rolls-page/rolls-page.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_6__services_rolls_service__["a" /* RollsService */],
            __WEBPACK_IMPORTED_MODULE_3_ngx_modal_dialog__["b" /* ModalDialogService */],
            __WEBPACK_IMPORTED_MODULE_0__angular_core__["_13" /* ViewContainerRef */],
            __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* Router */],
            __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* ActivatedRoute */],
            __WEBPACK_IMPORTED_MODULE_10__app_shared_services_app_modal_service__["a" /* AppModalService */]])
    ], RollsPageComponent);
    return RollsPageComponent;
}());



/***/ }),

/***/ "./src/app/modules/app-rolls/enums/check-status.enum.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CheckStatus; });
var CheckStatus;
(function (CheckStatus) {
    CheckStatus["NOT_CHECKED"] = "NOT_CHECKED";
    CheckStatus["CONFIRMED"] = "CONFIRMED";
    CheckStatus["NOT_CONFIRMED"] = "NOT_CONFIRMED";
})(CheckStatus || (CheckStatus = {}));


/***/ }),

/***/ "./src/app/modules/app-rolls/enums/roll-operation-type.enum.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollOperationType; });
var RollOperationType;
(function (RollOperationType) {
    RollOperationType["MANUFACTURE"] = "MANUFACTURE";
    RollOperationType["USE"] = "USE";
})(RollOperationType || (RollOperationType = {}));


/***/ }),

/***/ "./src/app/modules/app-rolls/rolls-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollsRouting; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_rolls_page_rolls_page_component__ = __webpack_require__("./src/app/modules/app-rolls/components/rolls-page/rolls-page.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_roll_operations_page_roll_operations_page_component__ = __webpack_require__("./src/app/modules/app-rolls/components/roll-operations-page/roll-operations-page.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var rollsRoutes = [{
        path: '',
        component: __WEBPACK_IMPORTED_MODULE_2__components_rolls_page_rolls_page_component__["a" /* RollsPageComponent */]
    },
    {
        path: 'operations',
        component: __WEBPACK_IMPORTED_MODULE_3__components_roll_operations_page_roll_operations_page_component__["a" /* RollOperationsPageComponent */]
    }
];
var RollsRouting = /** @class */ (function () {
    function RollsRouting() {
    }
    RollsRouting = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["J" /* NgModule */])({
            imports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["d" /* RouterModule */].forChild(rollsRoutes)],
            exports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["d" /* RouterModule */]]
        })
    ], RollsRouting);
    return RollsRouting;
}());



/***/ }),

/***/ "./src/app/modules/app-rolls/rolls.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RollsModule", function() { return RollsModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("./node_modules/@angular/common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_ngx_color_picker__ = __webpack_require__("./node_modules/ngx-color-picker/dist/ngx-color-picker.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__ng_bootstrap_ng_bootstrap__ = __webpack_require__("./node_modules/@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__ng_select_ng_select__ = __webpack_require__("./node_modules/@ng-select/ng-select/esm5/ng-select.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_ngx_contextmenu__ = __webpack_require__("./node_modules/ngx-contextmenu/lib/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_ngx_modal_dialog__ = __webpack_require__("./node_modules/ngx-modal-dialog/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__components_rolls_page_rolls_page_component__ = __webpack_require__("./src/app/modules/app-rolls/components/rolls-page/rolls-page.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__components_roll_type_modal_roll_type_modal_component__ = __webpack_require__("./src/app/modules/app-rolls/components/roll-type-modal/roll-type-modal.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__components_roll_operation_modal_roll_operation_modal_component__ = __webpack_require__("./src/app/modules/app-rolls/components/roll-operation-modal/roll-operation-modal.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__rolls_routing_module__ = __webpack_require__("./src/app/modules/app-rolls/rolls-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__services_rolls_service__ = __webpack_require__("./src/app/modules/app-rolls/services/rolls.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__services_rolls_url_service__ = __webpack_require__("./src/app/modules/app-rolls/services/rolls-url.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__components_roll_check_roll_check_component__ = __webpack_require__("./src/app/modules/app-rolls/components/roll-check/roll-check.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__app_shared_app_shared_module__ = __webpack_require__("./src/app/modules/app-shared/app-shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__components_roll_operations_page_roll_operations_page_component__ = __webpack_require__("./src/app/modules/app-rolls/components/roll-operations-page/roll-operations-page.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

















var RollsModule = /** @class */ (function () {
    function RollsModule() {
    }
    RollsModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["J" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_8__components_rolls_page_rolls_page_component__["a" /* RollsPageComponent */],
                __WEBPACK_IMPORTED_MODULE_9__components_roll_type_modal_roll_type_modal_component__["a" /* RollTypeModalComponent */],
                __WEBPACK_IMPORTED_MODULE_10__components_roll_operation_modal_roll_operation_modal_component__["a" /* RollOperationModalComponent */],
                __WEBPACK_IMPORTED_MODULE_14__components_roll_check_roll_check_component__["a" /* RollCheckComponent */],
                __WEBPACK_IMPORTED_MODULE_16__components_roll_operations_page_roll_operations_page_component__["a" /* RollOperationsPageComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_common__["b" /* CommonModule */],
                __WEBPACK_IMPORTED_MODULE_4__ng_bootstrap_ng_bootstrap__["a" /* NgbModule */].forRoot(),
                __WEBPACK_IMPORTED_MODULE_11__rolls_routing_module__["a" /* RollsRouting */],
                __WEBPACK_IMPORTED_MODULE_3_ngx_color_picker__["a" /* ColorPickerModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* ReactiveFormsModule */],
                __WEBPACK_IMPORTED_MODULE_5__ng_select_ng_select__["a" /* NgSelectModule */],
                __WEBPACK_IMPORTED_MODULE_6_ngx_contextmenu__["b" /* ContextMenuModule */].forRoot({
                    useBootstrap4: true
                }),
                __WEBPACK_IMPORTED_MODULE_7_ngx_modal_dialog__["a" /* ModalDialogModule */].forRoot(),
                __WEBPACK_IMPORTED_MODULE_15__app_shared_app_shared_module__["a" /* AppSharedModule */]
            ],
            entryComponents: [
                __WEBPACK_IMPORTED_MODULE_9__components_roll_type_modal_roll_type_modal_component__["a" /* RollTypeModalComponent */],
                __WEBPACK_IMPORTED_MODULE_10__components_roll_operation_modal_roll_operation_modal_component__["a" /* RollOperationModalComponent */]
            ],
            providers: [
                __WEBPACK_IMPORTED_MODULE_12__services_rolls_service__["a" /* RollsService */],
                __WEBPACK_IMPORTED_MODULE_13__services_rolls_url_service__["a" /* RollsUrlService */]
            ]
        })
    ], RollsModule);
    return RollsModule;
}());



/***/ }),

/***/ "./src/app/modules/app-rolls/services/rolls-url.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollsUrlService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__services_rest_details_service__ = __webpack_require__("./src/app/services/rest-details-service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var RollsUrlService = /** @class */ (function () {
    function RollsUrlService(restDetails) {
        this.restDetails = restDetails;
        this.host = this.restDetails.host;
        this.rollTypesUrl = this.host + "/roll-types";
        this.rollBatchUrl = this.host + "/roll-batches";
        this.rollLeftoverUrl = this.host + "/roll-leftovers";
        this.rollOperationUrl = this.host + "/roll-operations";
        this.rollChecksUrl = this.host + "/roll-checks";
    }
    RollsUrlService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_rest_details_service__["a" /* RestDetailsService */]])
    ], RollsUrlService);
    return RollsUrlService;
}());



/***/ }),

/***/ "./src/app/modules/app-rolls/services/rolls.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RollsService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__rolls_url_service__ = __webpack_require__("./src/app/modules/app-rolls/services/rolls-url.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_observable_from__ = __webpack_require__("./node_modules/rxjs/_esm5/observable/from.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_rxjs_observable_of__ = __webpack_require__("./node_modules/rxjs/_esm5/observable/of.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__ = __webpack_require__("./src/app/app-utils/app-date-utils.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__ = __webpack_require__("./src/app/app-utils/app-http-error-handler.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__enums_check_status_enum__ = __webpack_require__("./src/app/modules/app-rolls/enums/check-status.enum.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};








var RollsService = /** @class */ (function () {
    function RollsService(urls, http) {
        this.urls = urls;
        this.http = http;
        this.headers = new __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["c" /* HttpHeaders */]();
    }
    RollsService.prototype.postRollOperation = function (rollOperation) {
        return this.http.post(this.urls.rollOperationUrl, rollOperation).catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.putRollType = function (rollType) {
        var requestUrl = this.urls.rollTypesUrl + "/" + String(rollType.id);
        var dto = {
            note: rollType.note,
            colorCode: rollType.colorCode,
            thickness: rollType.thickness,
            minWeight: rollType.minWeight,
            maxWeight: rollType.maxWeight
        };
        return this.http.put(requestUrl, dto).catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.getRollsInfo = function (restDate, fromDate, totalDate) {
        var _this = this;
        return this.http.get(this.urls.rollTypesUrl, {
            headers: this.headers
        }).flatMap(function (data) { return Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_observable_from__["a" /* from */])(data)
            .flatMap(function (type) { return _this.getRollBatchesByDateRange(type.id, fromDate, totalDate)
            .flatMap(function (batches) { return _this.getRollLeftoverByRollIdAndDate(type.id, restDate)
            .flatMap(function (restOver) { return _this.getRollLeftoverByRollIdAndDate(type.id, totalDate)
            .flatMap(function (totalOver) { return _this.getRollCheck(type.id)
            .flatMap(function (rollCheck) {
            var rollInfo = {
                rollType: type,
                rollBatches: batches,
                restRollLeftover: restOver,
                totalRollLeftover: totalOver,
                rollCheck: rollCheck
            };
            return Object(__WEBPACK_IMPORTED_MODULE_4_rxjs_observable_of__["a" /* of */])(rollInfo);
        }); }); }); }); }); }).toArray().catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.getRollsInfoWithoutCheck = function (restDate, fromDate, totalDate) {
        var _this = this;
        return this.http.get(this.urls.rollTypesUrl, {
            headers: this.headers
        }).flatMap(function (data) { return Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_observable_from__["a" /* from */])(data)
            .flatMap(function (type) { return _this.getRollBatchesByDateRange(type.id, fromDate, totalDate)
            .flatMap(function (batches) { return _this.getRollLeftoverByRollIdAndDate(type.id, restDate)
            .flatMap(function (restOver) { return _this.getRollLeftoverByRollIdAndDate(type.id, totalDate)
            .flatMap(function (totalOver) { return Object(__WEBPACK_IMPORTED_MODULE_4_rxjs_observable_of__["a" /* of */])({
            id: undefined,
            rollTypeId: undefined,
            rollLeftOverCheckStatus: __WEBPACK_IMPORTED_MODULE_7__enums_check_status_enum__["a" /* CheckStatus */].NOT_CHECKED
        })
            .flatMap(function (rollCheck) {
            var rollInfo = {
                rollType: type,
                rollBatches: batches,
                restRollLeftover: restOver,
                totalRollLeftover: totalOver,
                rollCheck: rollCheck
            };
            return Object(__WEBPACK_IMPORTED_MODULE_4_rxjs_observable_of__["a" /* of */])(rollInfo);
        }); }); }); }); }); }).toArray().catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.postRollType = function (rollType, daysInTable, restDate, toDate) {
        return this.http.post(this.urls.rollTypesUrl, rollType, {
            headers: this.headers
        }).map(function (createdRollType) {
            var info = {
                rollType: createdRollType,
                rollBatches: new Array(daysInTable),
                restRollLeftover: {
                    date: Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(restDate),
                    rollTypeId: createdRollType.id,
                    amount: 0
                },
                totalRollLeftover: {
                    date: Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(toDate),
                    rollTypeId: createdRollType.id,
                    amount: 0
                },
                rollCheck: {
                    id: createdRollType.id,
                    rollTypeId: createdRollType.id,
                    rollLeftOverCheckStatus: __WEBPACK_IMPORTED_MODULE_7__enums_check_status_enum__["a" /* CheckStatus */].NOT_CHECKED
                }
            };
            return info;
        }).catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.getRollBatchesByDateRange = function (rollTypeId, from, to) {
        var params = new __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["d" /* HttpParams */]({
            fromObject: {
                roll_type_id: String(rollTypeId),
                from: Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(from),
                to: Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(to)
            }
        });
        return this.http.get(this.urls.rollBatchUrl, {
            params: params,
            headers: this.headers
        }).catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.getRollCheck = function (rollTypeId) {
        var params = new __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["d" /* HttpParams */]()
            .append('roll_type_id', String(rollTypeId));
        return this.http.get(this.urls.rollChecksUrl, {
            params: params,
            headers: this.headers
        }).catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.getRollLeftoverByRollIdAndDate = function (rollTypeId, date) {
        var params = new __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["d" /* HttpParams */]({
            fromObject: {
                roll_type_id: String(rollTypeId),
                date: Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(date)
            }
        });
        return this.http.get(this.urls.rollLeftoverUrl, {
            params: params,
            headers: this.headers
        }).catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.putRollChecks = function (rollChecks) {
        var _this = this;
        return Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_observable_from__["a" /* from */])(rollChecks).flatMap(function (value) { return _this.putRollChek(value); }).toArray();
    };
    RollsService.prototype.putRollChek = function (rollCheck) {
        var url = this.urls.rollChecksUrl + "/" + rollCheck.id;
        var body = {
            rollLeftOverCheckStatus: rollCheck.rollLeftOverCheckStatus
        };
        return this.http.put(url, body).catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.getRollOperations = function (rollTypeId, from, to) {
        var params = new __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["d" /* HttpParams */]()
            .set('roll_type_id', String(rollTypeId))
            .set('from_manuf', from)
            .set('to_manuf', to);
        return this.http.get(this.urls.rollOperationUrl, {
            params: params,
            headers: this.headers
        }).catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService.prototype.deleteRollType = function (rollTypeId) {
        var url = this.urls.rollTypesUrl + "/" + rollTypeId;
        return this.http.delete(url)
            .catch(__WEBPACK_IMPORTED_MODULE_6__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    RollsService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__rolls_url_service__["a" /* RollsUrlService */], __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["a" /* HttpClient */]])
    ], RollsService);
    return RollsService;
}());



/***/ })

});
//# sourceMappingURL=rolls.module.chunk.js.map
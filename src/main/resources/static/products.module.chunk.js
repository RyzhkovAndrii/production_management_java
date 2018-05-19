webpackJsonp(["products.module"],{

/***/ "./src/app/modules/app-products/components/product-operation-modal/product-operation-modal.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/modules/app-products/components/product-operation-modal/product-operation-modal.component.html":
/***/ (function(module, exports) {

module.exports = "<form [formGroup]=\"form\">\n  <div class=\"form-group\">\n    <label for=\"operation-type\">Тип операции:</label>\n    <select class=\"form-control\" id=\"operation-type\" formControlName=\"operationType\">\n      <option value=\"MANUFACTURED\">Производство</option>\n      <option value=\"SOLD\">Реализация</option>\n    </select>\n  </div>\n  <div class=\"form-group\">\n    <label for=\"product-amount\">Количество продуктов:</label>\n    <input type=\"number\" min=\"0\" step=\"1\" class=\"form-control\" id=\"product-amount\" formControlName=\"amount\" [ngClass]=\"{\n        'is-invalid': form.get('amount').invalid && isTouched('amount'),\n        'is-valid': form.get('amount').valid && isTouched('amount')\n      }\">\n    <div class=\"invalid-feedback\" *ngIf=\"form.get('amount').invalid && isTouched('amount')\">\n      <span *ngIf=\"form.get('amount').errors['required']\">\n        Обязательное поле!\n      </span>\n      <span *ngIf=\"form.get('amount').errors['min']\">\n        Минимальное количество продукции для операции: {{ MIN_PRODUCT_AMOUNT }}\n      </span>\n      <span *ngIf=\"form.get('amount').errors['decimalPlacesError']\">\n        Максимальное количество знаков после запятой: {{ DECIMAL_PLACES }}\n      </span>\n    </div>\n  </div>\n</form>\n"

/***/ }),

/***/ "./src/app/modules/app-products/components/product-operation-modal/product-operation-modal.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProductOperationModalComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_decimal_js__ = __webpack_require__("./node_modules/decimal.js/decimal.mjs");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__enums_product_operation_type_enum__ = __webpack_require__("./src/app/modules/app-products/enums/product-operation-type.enum.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ProductOperationModalComponent = /** @class */ (function () {
    function ProductOperationModalComponent() {
        this.MIN_PRODUCT_AMOUNT = 0.001;
        this.DECIMAL_PLACES = 3;
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
    ProductOperationModalComponent.prototype.ngOnInit = function () {
    };
    ProductOperationModalComponent.prototype.dialogInit = function (reference, options) {
        this.options = options;
        this.productOperation = options.data.productOperationRequest;
        this.operationType = __WEBPACK_IMPORTED_MODULE_3__enums_product_operation_type_enum__["a" /* ProductOperationType */][this.productOperation.operationType];
        this.form = new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["b" /* FormGroup */]({
            operationType: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */]({
                value: this.operationType,
                disabled: true
            }),
            amount: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](undefined, [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].min(this.MIN_PRODUCT_AMOUNT), this.validateDecimalPlaces.bind(this)])
        });
    };
    ProductOperationModalComponent.prototype.onSubmit = function () {
        if (this.form.invalid) {
            this.submitPressed = true;
            var reject = Promise.reject('invalid');
            this.options.data.func(reject);
            return reject;
        }
        var productOperation = {
            operationDate: this.productOperation.operationDate,
            productTypeId: this.productOperation.productTypeId,
            operationType: this.operationType,
            amount: new __WEBPACK_IMPORTED_MODULE_2_decimal_js__["a" /* Decimal */](this.form.value.amount).times(Math.pow(10, this.DECIMAL_PLACES)).toNumber()
        };
        var resolve = Promise.resolve(productOperation);
        this.options.data.func(resolve);
        return resolve;
    };
    ProductOperationModalComponent.prototype.isTouched = function (controlName) {
        return this.form.get(controlName).touched || this.submitPressed;
    };
    ProductOperationModalComponent.prototype.validateDecimalPlaces = function (control) {
        if (control.value && !new __WEBPACK_IMPORTED_MODULE_2_decimal_js__["a" /* Decimal */](control.value).times(Math.pow(10, this.DECIMAL_PLACES)).isInteger()) {
            return {
                'decimalPlacesError': true
            };
        }
        return null;
    };
    ProductOperationModalComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-product-operation-modal',
            template: __webpack_require__("./src/app/modules/app-products/components/product-operation-modal/product-operation-modal.component.html"),
            styles: [__webpack_require__("./src/app/modules/app-products/components/product-operation-modal/product-operation-modal.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ProductOperationModalComponent);
    return ProductOperationModalComponent;
}());



/***/ }),

/***/ "./src/app/modules/app-products/components/product-type-modal/product-type-modal.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/modules/app-products/components/product-type-modal/product-type-modal.component.html":
/***/ (function(module, exports) {

module.exports = "<form [formGroup]=\"form\">\n  <div class=\"row\">\n    <div class=\"col-md-6\">\n      <label for=\"roll-name\" class=\"col-form-label\">Наименование:</label>\n      <input type=\"text\" class=\"form-control\" id=\"roll-name\" formControlName=\"name\" [ngClass]=\"{\n            'is-invalid': form.get('name').invalid && isTouched('name'),\n            'is-valid': form.get('name').valid && isTouched('name')\n          }\">\n      <div class=\"invalid-feedback\" *ngIf=\"form.get('name').invalid && isTouched('name')\">\n        <span *ngIf=\"form.get('name').errors['required']\">Обязательное поле!</span>\n      </div>\n    </div>\n    <div class=\"col-md-4\">\n      <label for=\"product-weight\" class=\"col-form-label\">Вес:</label>\n      <input type=\"number\" class=\"form-control\" id=\"product-weight\" [step]=\"MIN_WEIGHT\" formControlName=\"weight\" [min]=\"MIN_WEIGHT\" [ngClass]=\"{\n          'is-invalid': form.get('weight').invalid && isTouched('weight'),\n          'is-valid': form.get('weight').valid && isTouched('weight')\n        }\">\n      <div class=\"invalid-feedback\" *ngIf=\"form.get('weight').invalid && isTouched('weight')\">\n        <span *ngIf=\"form.get('weight').errors['required']\">Обязательное поле!</span>\n        <span *ngIf=\"form.get('weight').errors['min']\">Вес должен быть больше {{ MIN_WEIGHT }}</span>\n      </div>\n    </div>\n    <div class=\"col-md-2\">\n      <label for=\"product-color\" class=\"col-form-label\">Цвет:</label>\n      <input type=\"button\" class=\"form-control\" id=\"product-color\" formControlName=\"colorCode\" [(colorPicker)]=\"colorCode\" [value]=\"colorCode\"\n        [style.background]=\"colorCode\" [style.color]=\"colorCode\" [cpOutputFormat]=\"'hex'\" [cpPosition]=\"'left'\" [cpOKButton]=\"true\"\n        [cpCancelButton]=\"true\" [cpPresetColors]=\"presetColors\" [cpWidth]=\"300\" [cpPositionOffset]=\"-500\">\n    </div>\n  </div>\n</form>\n"

/***/ }),

/***/ "./src/app/modules/app-products/components/product-type-modal/product-type-modal.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProductTypeModalComponent; });
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



var ProductTypeModalComponent = /** @class */ (function () {
    function ProductTypeModalComponent() {
        this.submitPressed = false;
        this.presetColors = __WEBPACK_IMPORTED_MODULE_2__app_utils_app_preset_colors__["a" /* default */];
        this.btnClass = 'btn btn-outline-dark';
        this.MIN_WEIGHT = 0.1;
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
    ProductTypeModalComponent.prototype.ngOnInit = function () {
        this.colorCode = this.productType ? this.productType.colorCode : __WEBPACK_IMPORTED_MODULE_2__app_utils_app_preset_colors__["a" /* default */][0];
        this.form = new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["b" /* FormGroup */]({
            name: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](this.productType ? this.productType.name : undefined, [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].required]),
            weight: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](this.productType ? this.productType.weight : undefined, [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].min(this.MIN_WEIGHT)]),
            colorCode: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](this.colorCode)
        });
    };
    ProductTypeModalComponent.prototype.dialogInit = function (reference, options) {
        this.options = options;
        this.productType = options.data.productType;
    };
    ProductTypeModalComponent.prototype.onSubmit = function () {
        if (this.form.invalid) {
            this.submitPressed = true;
            var reject = Promise.reject('invalid');
            this.options.data.operation(reject);
            return reject;
        }
        var type = {
            name: this.form.value.name,
            weight: this.form.value.weight,
            colorCode: this.colorCode
        };
        var resolve = Promise.resolve(type);
        this.options.data.operation(resolve);
        return resolve;
    };
    ProductTypeModalComponent.prototype.isTouched = function (controlName) {
        return this.form.get(controlName).touched || this.submitPressed;
    };
    ProductTypeModalComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-product-type-modal',
            template: __webpack_require__("./src/app/modules/app-products/components/product-type-modal/product-type-modal.component.html"),
            styles: [__webpack_require__("./src/app/modules/app-products/components/product-type-modal/product-type-modal.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ProductTypeModalComponent);
    return ProductTypeModalComponent;
}());



/***/ }),

/***/ "./src/app/modules/app-products/components/products-page/products-page.component.css":
/***/ (function(module, exports) {

module.exports = "td {\n    width: 12%;\n}\n\n.empty-row, .empty-row:hover {\n    height: 1%;\n    background-color: white;\n    border: none;\n}\n\n::ng-deep .dropdown-item {\n    font-size: 0.9rem;\n    padding: .1rem 1rem;\n  }\n"

/***/ }),

/***/ "./src/app/modules/app-products/components/products-page/products-page.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid mt-2\">\n  <div class=\"row\">\n    <div class=\"col-md-1\">\n      <button class=\"btn btn-outline-dark btn-sm\" (click)=\"openAddProductTypeModal()\">\n        <span class=\"material-icons\">add</span>\n      </button>\n    </div>\n\n    <div class=\"col-md-3 offset-md-4\">\n      <form [formGroup]=\"form\">\n        <div class=\"form-row\">\n          <div class=\"pl-2 pr-2 pt-1 pb-1 pt-md-0 pb-md-0\">\n            <div class=\"input-group\">\n              <input type=\"date\" class=\"form-control form-control-sm\" id=\"daylyDate\" formControlName=\"daylyDate\" [ngClass]=\"{\n  'is-invalid': form.get('daylyDate').invalid,\n  'is-valid': form.get('daylyDate').valid && form.get('daylyDate').touched\n  }\">\n              <div class=\"invalid-feedback\" *ngIf=\"form.get('daylyDate').invalid\">\n                <span *ngIf=\"form.get('daylyDate').errors['required']\">Не может быть пустым!</span>\n              </div>\n            </div>\n          </div>\n\n          <div class=\"pl-2 pr-2 pt-1 pb-1 pt-md-0 pb-md-0\">\n            <button type=\"submit\" class=\"btn btn-outline-dark btn-sm\" [disabled]=\"form.invalid\" (click)=\"loadTable()\">Сменить дату</button>\n          </div>\n        </div>\n      </form>\n    </div>\n  </div>\n\n  <div class=\"table-responsive mt-2\">\n    <table class=\"table table-bordered table-striped table-sm\">\n      <thead>\n        <tr class=\"table-header\">\n          <th scope=\"col\" rowspan=\"2\" class=\"align-middle\">Наименование</th>\n          <th scope=\"col\" rowspan=\"2\" class=\"align-middle\">Вес</th>\n          <th scope=\"col\" rowspan=\"2\" class=\"align-middle\">Цвет</th>\n          <th scope=\"col\" rowspan=\"2\" class=\"align-middle\">Остаток на {{ fromDate | moment:'DD MMMM' }}, тыс.шт.</th>\n          <th scope=\"col\" rowspan=\"1\" colspan=\"2\">Произведено тыс.шт.</th>\n          <th scope=\"col\" rowspan=\"1\" colspan=\"2\">Реализация тыс.шт.</th>\n          <th scope=\"col\" rowspan=\"2\" class=\"align-middle\">Остаток на {{ toDate | moment:'DD MMMM' }}, тыс.шт.</th>\n        </tr>\n        <tr class=\"table-header\">\n          <th scope=\"col\">{{ daylyDate | moment:'DD MMMM' }}</th>\n          <th scope=\"col\">{{ daylyDate | moment:'MMMM' }}</th>\n          <th scope=\"col\">{{ daylyDate | moment:'DD MMMM' }}</th>\n          <th scope=\"col\">{{ daylyDate | moment:'MMMM' }}</th>\n        </tr>\n      </thead>\n      <tbody *ngFor=\"let infoArray of sortByColor(productsInfo)\">\n        <tr *ngFor=\"let productInfo of sortByNameAndWeight(infoArray)\" [contextMenu]=\"productsMenu\" [contextMenuSubject]=\"productInfo.type\">\n          <th scope=\"row\" class=\"row-name\">\n            {{ productInfo.type.name }}\n          </th>\n          <th scope=\"row\" class=\"row-name\">\n            {{ productInfo.type.weight }}\n          </th>\n          <th scope=\"row\" class=\"row-name\">\n            <span class=\"filled-circle\" [ngStyle]=\"{ 'background-color': productInfo.type.colorCode }\"></span>\n          </th>\n          <td>\n            {{ (productInfo.restLeftover.amount) | exponent }}\n          </td>\n          <td (click)=\"openAddProductOperation(productInfo.type.id, 'MANUFACTURED')\">\n            {{ productInfo.dayBatch.manufacturedAmount | empty | exponent }}\n          </td>\n          <td>\n            {{ productInfo.monthBatch.manufacturedAmount | empty | exponent }}\n          </td>\n          <td (click)=\"openAddProductOperation(productInfo.type.id, 'SOLD')\">\n            {{ productInfo.dayBatch.soldAmount | empty | exponent }}\n          </td>\n          <td>\n            {{ productInfo.monthBatch.soldAmount | empty | exponent }}\n          </td>\n          <td>\n            {{ productInfo.currentLeftover.amount | exponent }}\n          </td>\n        </tr>\n        <tr>\n          <th scope=\"row\" class=\"row-name\" colspan=\"3\">Итого</th>\n          <td *ngFor=\"let total of getSectionTotals(infoArray)\">\n            {{ total | exponent }}\n          </td>\n        </tr>\n        <td class=\"empty-row\"></td>\n      </tbody>\n      <tbody *ngIf=\"productsInfo.length != 0\">\n        <tr>\n          <th scope=\"row\" class=\"row-name\" colspan=\"3\">Всего</th>\n          <td *ngFor=\"let total of getTotals()\">\n            {{ total | exponent }}\n          </td>\n        </tr>\n      </tbody>\n    </table>\n  </div>\n</div>\n\n<context-menu #productsMenu>\n  <ng-template contextMenuItem let-item (execute)=\"openEditProductTypeModal($event.item)\">\n    Редактировать продукцию\n  </ng-template>\n  <ng-template contextMenuItem divider=\"true\"></ng-template>\n  <ng-template contextMenuItem let-item (execute)=\"openDeleteProductTypeModal($event.item)\">\n    Удалить продукцию\n  </ng-template>\n</context-menu>\n"

/***/ }),

/***/ "./src/app/modules/app-products/components/products-page/products-page.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProductsPageComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_ngx_modal_dialog__ = __webpack_require__("./node_modules/ngx-modal-dialog/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_products_service__ = __webpack_require__("./src/app/modules/app-products/services/products.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__ = __webpack_require__("./src/app/app-utils/app-date-utils.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__app_shared_services_app_modal_service__ = __webpack_require__("./src/app/modules/app-shared/services/app-modal.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__product_type_modal_product_type_modal_component__ = __webpack_require__("./src/app/modules/app-products/components/product-type-modal/product-type-modal.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__app_shared_components_simple_confirm_modal_simple_confirm_modal_component__ = __webpack_require__("./src/app/modules/app-shared/components/simple-confirm-modal/simple-confirm-modal.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__product_operation_modal_product_operation_modal_component__ = __webpack_require__("./src/app/modules/app-products/components/product-operation-modal/product-operation-modal.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__app_utils_app_comparators__ = __webpack_require__("./src/app/app-utils/app-comparators.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};










var ProductsPageComponent = /** @class */ (function () {
    function ProductsPageComponent(productsService, viewRef, ngxModalDialogService, appModalService) {
        this.productsService = productsService;
        this.viewRef = viewRef;
        this.ngxModalDialogService = ngxModalDialogService;
        this.appModalService = appModalService;
        this.productsInfo = [];
        this.COLLATOR = new Intl.Collator([], {
            sensitivity: "base"
        });
    }
    ProductsPageComponent.prototype.ngOnInit = function () {
        this.form = new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["b" /* FormGroup */]({
            daylyDate: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["a" /* FormControl */](Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["d" /* formatDateServerToBrowser */])(Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["b" /* formatDate */])(Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["j" /* midnightDate */])())), __WEBPACK_IMPORTED_MODULE_1__angular_forms__["g" /* Validators */].required)
        });
        this.loadTable();
    };
    ProductsPageComponent.prototype.loadTable = function () {
        this.initDates();
        this.fetchData();
    };
    ProductsPageComponent.prototype.initDates = function () {
        this.daylyDate = Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["e" /* getDate */])(this.form.value.daylyDate, 'YYYY-MM-DD');
        this.fromDate = Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["f" /* getDateFirstDayOfMonth */])(this.daylyDate);
        if (Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["i" /* isSameMonthYear */])(this.daylyDate, Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["j" /* midnightDate */])())) {
            this.toDate = Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["j" /* midnightDate */])();
        }
        else {
            this.toDate = Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["g" /* getDateLastDayOfMotth */])(this.daylyDate);
        }
    };
    ProductsPageComponent.prototype.fetchData = function () {
        var _this = this;
        this.productsService.getProductsInfo(this.daylyDate, this.fromDate, this.toDate)
            .subscribe(function (data) {
            _this.productsInfo = data;
        }, function (error) {
            _this.appModalService.openHttpErrorModal(_this.ngxModalDialogService, _this.viewRef, error);
        });
    };
    ProductsPageComponent.prototype.sortByColor = function (array) {
        return array.sort(function (a, b) {
            var byColor = Object(__WEBPACK_IMPORTED_MODULE_9__app_utils_app_comparators__["a" /* compareColors */])(a[0].type.colorCode, b[0].type.colorCode);
            return byColor != 0 ? byColor : 1;
        });
    };
    ProductsPageComponent.prototype.sortByNameAndWeight = function (array) {
        var _this = this;
        return array.sort(function (a, b) {
            var byName = _this.COLLATOR.compare(a.type.name, b.type.name);
            return byName != 0 ? byName : a.type.weight - b.type.weight;
        });
    };
    ProductsPageComponent.prototype.getSectionTotals = function (values) {
        return values.reduce(function (previousValue, currentValue, currentIndex, array) {
            previousValue[0] += currentValue.restLeftover.amount;
            previousValue[1] += currentValue.dayBatch.manufacturedAmount || 0;
            previousValue[2] += currentValue.monthBatch.manufacturedAmount || 0;
            previousValue[3] += currentValue.dayBatch.soldAmount || 0;
            previousValue[4] += currentValue.monthBatch.soldAmount || 0;
            previousValue[5] += currentValue.currentLeftover.amount;
            return previousValue;
        }, new Array(6).fill(0, 0));
    };
    ProductsPageComponent.prototype.getTotals = function () {
        var _this = this;
        return this.productsInfo.reduce(function (previousValue, currentValue, currentIndex, array) {
            var sectionTotals = _this.getSectionTotals(currentValue);
            return previousValue.map(function (value, index, array) { return array[index] += sectionTotals[index]; });
        }, new Array(6).fill(0, 0));
    };
    ProductsPageComponent.prototype.openAddProductTypeModal = function () {
        var _this = this;
        var operation = function (result) {
            result
                .then(function (resolve) {
                _this.productsService.postProductType(resolve)
                    .subscribe(function (data) { return _this.fetchData(); }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalDialogService, _this.viewRef, error); });
            }, function (reject) { });
        };
        var modalOptions = {
            title: 'Новая продукция',
            childComponent: __WEBPACK_IMPORTED_MODULE_6__product_type_modal_product_type_modal_component__["a" /* ProductTypeModalComponent */],
            data: {
                operation: operation.bind(this)
            }
        };
        this.ngxModalDialogService.openDialog(this.viewRef, modalOptions);
    };
    ProductsPageComponent.prototype.openEditProductTypeModal = function (productType) {
        var _this = this;
        var operation = function (result) {
            result
                .then(function (resolve) {
                _this.productsService.putProductType(productType.id, resolve)
                    .subscribe(function (data) { return _this.fetchData(); }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalDialogService, _this.viewRef, error); });
            }, function (reject) { });
        };
        var modalOptions = {
            title: 'Редактирование продукции',
            childComponent: __WEBPACK_IMPORTED_MODULE_6__product_type_modal_product_type_modal_component__["a" /* ProductTypeModalComponent */],
            data: {
                productType: {
                    name: productType.name,
                    weight: productType.weight,
                    colorCode: productType.colorCode
                },
                operation: operation.bind(this)
            }
        };
        this.ngxModalDialogService.openDialog(this.viewRef, modalOptions);
    };
    ProductsPageComponent.prototype.openAddProductOperation = function (productTypeId, operationType) {
        var _this = this;
        var func = function (result) {
            result
                .then(function (resolve) {
                _this.productsService.postProductOperation(resolve)
                    .subscribe(function (data) { return _this.fetchData(); }, function (error) { return _this.appModalService.openHttpErrorModal(_this.ngxModalDialogService, _this.viewRef, error); });
            }, function (reject) { });
        };
        var modalOptions = {
            title: 'Операция над продукцией',
            childComponent: __WEBPACK_IMPORTED_MODULE_8__product_operation_modal_product_operation_modal_component__["a" /* ProductOperationModalComponent */],
            data: {
                productOperationRequest: {
                    operationDate: Object(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_date_utils__["b" /* formatDate */])(this.daylyDate),
                    productTypeId: productTypeId,
                    operationType: operationType,
                    amount: undefined
                },
                func: func
            }
        };
        this.ngxModalDialogService.openDialog(this.viewRef, modalOptions);
    };
    ProductsPageComponent.prototype.openDeleteProductTypeModal = function (productType) {
        var _this = this;
        var buttonClass = 'btn btn-outline-dark';
        var modalOptions = {
            title: 'Подтвердите удаление продукции',
            childComponent: __WEBPACK_IMPORTED_MODULE_7__app_shared_components_simple_confirm_modal_simple_confirm_modal_component__["a" /* SimpleConfirmModalComponent */],
            actionButtons: [{
                    text: 'Отменить',
                    buttonClass: buttonClass,
                    onAction: function () { return true; }
                },
                {
                    text: 'Удалить',
                    buttonClass: buttonClass,
                    onAction: function () {
                        _this.productsService.deleteProductType(productType.id).subscribe(function (data) { return _this.fetchData(); }, function (error) {
                            _this.appModalService.openHttpErrorModal(_this.ngxModalDialogService, _this.viewRef, error);
                        });
                        return true;
                    }
                }
            ]
        };
        this.ngxModalDialogService.openDialog(this.viewRef, modalOptions);
    };
    ProductsPageComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-products-page',
            template: __webpack_require__("./src/app/modules/app-products/components/products-page/products-page.component.html"),
            styles: [__webpack_require__("./src/app/modules/app-products/components/products-page/products-page.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_3__services_products_service__["a" /* ProductsService */],
            __WEBPACK_IMPORTED_MODULE_0__angular_core__["_13" /* ViewContainerRef */],
            __WEBPACK_IMPORTED_MODULE_2_ngx_modal_dialog__["b" /* ModalDialogService */],
            __WEBPACK_IMPORTED_MODULE_5__app_shared_services_app_modal_service__["a" /* AppModalService */]])
    ], ProductsPageComponent);
    return ProductsPageComponent;
}());



/***/ }),

/***/ "./src/app/modules/app-products/enums/product-operation-type.enum.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProductOperationType; });
var ProductOperationType;
(function (ProductOperationType) {
    ProductOperationType["MANUFACTURED"] = "MANUFACTURED";
    ProductOperationType["SOLD"] = "SOLD";
})(ProductOperationType || (ProductOperationType = {}));


/***/ }),

/***/ "./src/app/modules/app-products/products-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProductsRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("./node_modules/@angular/router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_products_page_products_page_component__ = __webpack_require__("./src/app/modules/app-products/components/products-page/products-page.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var productsRoutes = [{
        path: '',
        component: __WEBPACK_IMPORTED_MODULE_2__components_products_page_products_page_component__["a" /* ProductsPageComponent */]
    }];
var ProductsRoutingModule = /** @class */ (function () {
    function ProductsRoutingModule() {
    }
    ProductsRoutingModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["J" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_router__["d" /* RouterModule */].forChild(productsRoutes)
            ],
            exports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_router__["d" /* RouterModule */]
            ]
        })
    ], ProductsRoutingModule);
    return ProductsRoutingModule;
}());



/***/ }),

/***/ "./src/app/modules/app-products/products.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProductsModule", function() { return ProductsModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("./node_modules/@angular/common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_ngx_color_picker__ = __webpack_require__("./node_modules/ngx-color-picker/dist/ngx-color-picker.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__ng_select_ng_select__ = __webpack_require__("./node_modules/@ng-select/ng-select/esm5/ng-select.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_ngx_contextmenu__ = __webpack_require__("./node_modules/ngx-contextmenu/lib/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_ngx_modal_dialog__ = __webpack_require__("./node_modules/ngx-modal-dialog/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__app_shared_app_shared_module__ = __webpack_require__("./src/app/modules/app-shared/app-shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__components_products_page_products_page_component__ = __webpack_require__("./src/app/modules/app-products/components/products-page/products-page.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__products_routing_module__ = __webpack_require__("./src/app/modules/app-products/products-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__services_products_urls_service__ = __webpack_require__("./src/app/modules/app-products/services/products-urls.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__services_products_service__ = __webpack_require__("./src/app/modules/app-products/services/products.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__components_product_type_modal_product_type_modal_component__ = __webpack_require__("./src/app/modules/app-products/components/product-type-modal/product-type-modal.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__components_product_operation_modal_product_operation_modal_component__ = __webpack_require__("./src/app/modules/app-products/components/product-operation-modal/product-operation-modal.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};














var ProductsModule = /** @class */ (function () {
    function ProductsModule() {
    }
    ProductsModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["J" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_common__["b" /* CommonModule */],
                __WEBPACK_IMPORTED_MODULE_9__products_routing_module__["a" /* ProductsRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_3_ngx_color_picker__["a" /* ColorPickerModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["c" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* ReactiveFormsModule */],
                __WEBPACK_IMPORTED_MODULE_4__ng_select_ng_select__["a" /* NgSelectModule */],
                __WEBPACK_IMPORTED_MODULE_5_ngx_contextmenu__["b" /* ContextMenuModule */].forRoot({
                    useBootstrap4: true
                }),
                __WEBPACK_IMPORTED_MODULE_6_ngx_modal_dialog__["a" /* ModalDialogModule */].forRoot(),
                __WEBPACK_IMPORTED_MODULE_7__app_shared_app_shared_module__["a" /* AppSharedModule */]
            ],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_8__components_products_page_products_page_component__["a" /* ProductsPageComponent */], __WEBPACK_IMPORTED_MODULE_12__components_product_type_modal_product_type_modal_component__["a" /* ProductTypeModalComponent */], __WEBPACK_IMPORTED_MODULE_13__components_product_operation_modal_product_operation_modal_component__["a" /* ProductOperationModalComponent */]
            ],
            providers: [
                __WEBPACK_IMPORTED_MODULE_10__services_products_urls_service__["a" /* ProductsUrlsService */],
                __WEBPACK_IMPORTED_MODULE_11__services_products_service__["a" /* ProductsService */]
            ],
            entryComponents: [
                __WEBPACK_IMPORTED_MODULE_12__components_product_type_modal_product_type_modal_component__["a" /* ProductTypeModalComponent */],
                __WEBPACK_IMPORTED_MODULE_13__components_product_operation_modal_product_operation_modal_component__["a" /* ProductOperationModalComponent */]
            ]
        })
    ], ProductsModule);
    return ProductsModule;
}());



/***/ }),

/***/ "./src/app/modules/app-products/services/products-urls.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProductsUrlsService; });
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


var ProductsUrlsService = /** @class */ (function () {
    function ProductsUrlsService(restDetails) {
        this.restDetails = restDetails;
        this.host = this.restDetails.host;
        this.productTypesUrl = this.host + "/product-types";
        this.productBatchUrl = this.host + "/product-batches";
        this.productLeftoverUrl = this.host + "/product-leftovers";
        this.productOperationUrl = this.host + "/product-operations";
        this.productChecksUrl = this.host + "/product-checks";
    }
    ProductsUrlsService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__services_rest_details_service__["a" /* RestDetailsService */]])
    ], ProductsUrlsService);
    return ProductsUrlsService;
}());



/***/ }),

/***/ "./src/app/modules/app-products/services/products.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProductsService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_observable_from__ = __webpack_require__("./node_modules/rxjs/_esm5/observable/from.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__products_urls_service__ = __webpack_require__("./src/app/modules/app-products/services/products-urls.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__ = __webpack_require__("./src/app/app-utils/app-http-error-handler.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__ = __webpack_require__("./src/app/app-utils/app-date-utils.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var ProductsService = /** @class */ (function () {
    function ProductsService(urls, http) {
        this.urls = urls;
        this.http = http;
    }
    ProductsService.prototype.getProductsInfo = function (daylyDate, fromDate, toDate) {
        var _this = this;
        return this.getProductsLeftovers(fromDate)
            .map(function (restOvers) { return new Map(restOvers.map(function (x) { return [x.productTypeId, x]; })); })
            .flatMap(function (restOversMap) { return _this.getDaylyBatches(daylyDate)
            .map(function (dayBatches) { return new Map(dayBatches.map(function (x) { return [x.productTypeId, x]; })); })
            .flatMap(function (dayBatchesMap) { return _this.getMonthlyBatches(fromDate, toDate)
            .map(function (monthBatches) { return new Map(monthBatches.map(function (x) { return [x.productTypeId, x]; })); })
            .flatMap(function (monthBatchesMap) { return _this.getProductsLeftovers(toDate)
            .map(function (currentOvers) { return new Map(currentOvers.map(function (x) { return [x.productTypeId, x]; })); })
            .flatMap(function (currentOversMap) { return _this.getProductsChecks()
            .map(function (checks) { return new Map(checks.map(function (x) { return [x.productTypeId, x]; })); })
            .flatMap(function (checksMap) { return _this.getProductTypes()
            .flatMap(function (types) { return Object(__WEBPACK_IMPORTED_MODULE_2_rxjs_observable_from__["a" /* from */])(types)
            .map(function (type) {
            var info = {
                type: type,
                restLeftover: restOversMap.get(type.id) || {},
                dayBatch: dayBatchesMap.get(type.id) || {},
                monthBatch: monthBatchesMap.get(type.id) || {},
                currentLeftover: currentOversMap.get(type.id) || {},
                productCheck: checksMap.get(type.id) || {}
            };
            return info;
        }); }).reduce(function (acc, value, index) {
            if (acc.has(value.type.colorCode.toLowerCase()))
                acc.get(value.type.colorCode.toLocaleLowerCase()).push(value);
            else
                acc.set(value.type.colorCode.toLocaleLowerCase(), [value]);
            return acc;
        }, new Map())
            .flatMap(function (infoMap) { return Array.from(infoMap.values()); }); }); }); }); }); }).toArray();
    };
    ProductsService.prototype.getProductTypes = function () {
        return this.http.get(this.urls.productTypesUrl).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.getProductsLeftovers = function (date) {
        var params = new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpParams */]()
            .set('date', Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(date));
        return this.http.get(this.urls.productLeftoverUrl, {
            params: params
        }).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.getProductLeftover = function (productTypeId, date) {
        var params = new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpParams */]()
            .set('product-type-id', String(productTypeId))
            .set('date', Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(date));
        return this.http.get(this.urls.productLeftoverUrl, {
            params: params
        }).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.getDaylyBatches = function (date) {
        var params = new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpParams */]()
            .set('date', Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(date));
        return this.http.get(this.urls.productBatchUrl, {
            params: params
        }).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.getDaylyBatch = function (productTypeId, date) {
        var params = new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpParams */]()
            .set('product-type-id', String(productTypeId))
            .set('date', Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(date));
        return this.http.get(this.urls.productBatchUrl, {
            params: params
        }).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.getMonthlyBatches = function (fromDate, toDate) {
        var params = new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpParams */]()
            .set('from', Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(fromDate))
            .set('to', Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(toDate));
        return this.http.get(this.urls.productBatchUrl, {
            params: params
        }).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.getMonthlyBatch = function (productTypeId, fromDate, toDate) {
        var params = new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpParams */]()
            .set('product-type-id', String(productTypeId))
            .set('from', Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(fromDate))
            .set('to', Object(__WEBPACK_IMPORTED_MODULE_5__app_utils_app_date_utils__["b" /* formatDate */])(toDate));
        return this.http.get(this.urls.productBatchUrl, {
            params: params
        }).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.getProductsChecks = function () {
        return this.http.get(this.urls.productChecksUrl).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.getProductCheck = function (productTypeId) {
        var params = new __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["d" /* HttpParams */]()
            .set('product_type_id', String(productTypeId));
        return this.http.get(this.urls.productChecksUrl, {
            params: params
        }).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.postProductType = function (type) {
        return this.http.post(this.urls.productTypesUrl, type).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.postProductOperation = function (operation) {
        return this.http.post(this.urls.productOperationUrl, operation).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.putProductType = function (id, type) {
        var url = this.urls.productTypesUrl + "/" + id;
        return this.http.put(url, type).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.putProductCheck = function (id, check) {
        var url = this.urls.productChecksUrl + "/" + id;
        return this.http.put(url, check).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService.prototype.deleteProductType = function (id) {
        var url = this.urls.productTypesUrl + "/" + id;
        return this.http.delete(url).catch(__WEBPACK_IMPORTED_MODULE_4__app_utils_app_http_error_handler__["a" /* httpErrorHandle */]);
    };
    ProductsService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_3__products_urls_service__["a" /* ProductsUrlsService */], __WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], ProductsService);
    return ProductsService;
}());



/***/ })

});
//# sourceMappingURL=products.module.chunk.js.map
const asideMenuComponent = function(firstMenuId, secondMenuId) {
    this._firstMenu = firstMenuId;
    this._secondMenu = secondMenuId;
}

asideMenuComponent.prototype.setMenu = function () {
    $(this._firstMenu).addClass('kt-menu__item--open');
    $(this._secondMenu).addClass('kt-menu__item--active');
}

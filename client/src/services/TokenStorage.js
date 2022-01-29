// LocalStorageService.js
export const TokenStorageService = (function(){
    var _service;
    function _getService() {
        if(!_service) {
            _service = this;
            return _service
        }
        return _service
    }
    function _setToken(tokenObj) {
        localStorage.setItem('access_token', tokenObj);
    }

    function _setRefreshToken(tokenObj) {
        localStorage.setItem('refresh_token', tokenObj);
    }

    function _setIsAuthenticated(flag) {
        localStorage.setItem('auth', flag)
    }
    function _getIsAuthenticated() {
        console.log(localStorage.getItem('auth'))
        return localStorage.getItem('auth')
    }

    function _getAccessToken() {
        return localStorage.getItem('access_token');
    }
    function _getRefreshToken() {
        return localStorage.getItem('refresh_token');
    }
    function _clearToken() {
        localStorage.removeItem('access_toke');
        localStorage.removeItem('refresh_token');
    }

    return {
        getService : _getService,
        setToken : _setToken,
        setRefreshToken : _setRefreshToken,
        getAccessToken : _getAccessToken,
        getRefreshToken : _getRefreshToken,
        clearToken : _clearToken,
        setIsAuthenticated : _setIsAuthenticated,
        getIsAuthenticated: _getIsAuthenticated,
    }
})();
export default TokenStorageService;
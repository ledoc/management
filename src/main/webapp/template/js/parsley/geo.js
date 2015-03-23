var validateGeoCoords = function(value) {
	var splitValue = value.split("/");
	if (splitValue.length == 2) {
		return IsNumeric(splitValue[0]) && IsNumeric(splitValue[1]);
	}
	return false;
};

function IsNumeric(input) {
	return (input - 0) == input && ('' + input).trim().length > 0;
};
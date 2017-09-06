package br.com.centaurotech

def delete(Map map = [:], server) {
    powershell "Invoke-Command -ComputerName $server -ScriptBlock  {Get-WMIObject -class Win32_UserProfile | Where {(!$_.Special) -and ($_.ConvertToDateTime($_.LastUseTime) -lt (Get-Date).AddDays(-5))} | Remove-WmiObject}"
}
"use client";

import { useCallback, useEffect, useMemo, useState } from "react";
import { fetcher, spotterFetcher } from "../../../lib/api";

type DashboardMode = "user" | "staff";
type Role = "USER" | "STAFF" | "ADMIN";
type Subscription = "FREE" | "PREMIUM";

type Account = {
    id: number;
    email: string;
    username: string;
    role: Role;
    enabled: boolean;
    createdAt: string;
    subscription?: Subscription | null;
};

type ZoneSummary = {
    lotName: string;
    zone: string;
    totalSpaces: number;
    occupiedSpaces: number;
    availableSpaces: number;
    occupancyRate: number;
};

type SpotterSummary = {
    totalSpaces: number;
    occupiedSpaces: number;
    availableSpaces: number;
    disabilityPermitSpaces: number;
    availableDisabilityPermitSpaces: number;
    occupancyRate: number;
    zones: ZoneSummary[];
};

type Space = {
    id: number;
    lotName: string;
    zone: string;
    bayNumber: string;
    displayName: string;
    sensorId: string;
    maxParkingMinutes: number;
    disabilityPermitRequired: boolean;
    occupied: boolean;
    confidence: number;
    statusSource: string;
    lastUpdated: string;
    latitude?: number | null;
    longitude?: number | null;
};

type DetectionEvent = {
    id: number;
    spaceId: number;
    sensorId: string;
    lotName: string;
    zone: string;
    bayNumber: string;
    previousOccupied: boolean;
    occupied: boolean;
    confidence: number;
    source: string;
    detectedAt: string;
};

type SimulationEvent = {
    sequenceNumber: number;
    space: Space;
    event: DetectionEvent;
};

type SimulationRunResponse = {
    appliedEvents: number;
    feedSize: number;
    nextFeedIndex: number;
    events: SimulationEvent[];
    summary: SpotterSummary;
};

type AccountStats = {
    total: number;
    users: number;
    staff: number;
    disabled: number;
    premium: number;
};

function toQuery(params: URLSearchParams) {
    const query = params.toString();
    return query ? `?${query}` : "";
}

function formatTime(value?: string | Date | null) {
    if (!value) {
        return "Waiting";
    }

    const date = value instanceof Date ? value : new Date(value);

    if (Number.isNaN(date.getTime())) {
        return "Waiting";
    }

    return new Intl.DateTimeFormat("en-AU", {
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
    }).format(date);
}

function formatDateTime(value?: string | null) {
    if (!value) {
        return "Not recorded";
    }

    const date = new Date(value);

    if (Number.isNaN(date.getTime())) {
        return "Not recorded";
    }

    return new Intl.DateTimeFormat("en-AU", {
        day: "2-digit",
        month: "short",
        hour: "2-digit",
        minute: "2-digit",
    }).format(date);
}

function percent(value: number) {
    return `${Math.round(value)}%`;
}

function metricValue(value: number | undefined) {
    return typeof value === "number" ? value.toLocaleString("en-AU") : "...";
}

function eventChangeText(event: DetectionEvent) {
    if (event.previousOccupied === event.occupied) {
        return event.occupied ? "Stayed occupied" : "Stayed free";
    }

    return event.occupied ? "Free to occupied" : "Occupied to free";
}

function accountStats(accounts: Account[]): AccountStats {
    return {
        total: accounts.length,
        users: accounts.filter((account) => account.role === "USER").length,
        staff: accounts.filter((account) => account.role === "STAFF" || account.role === "ADMIN").length,
        disabled: accounts.filter((account) => !account.enabled).length,
        premium: accounts.filter((account) => account.subscription === "PREMIUM").length,
    };
}

function clearJwtCookie() {
    document.cookie = "jwt=; Max-Age=0; path=/; SameSite=Lax";
}

function logout() {
    clearJwtCookie();
    window.location.href = "/login";
}

export default function SpotterDashboard({ mode }: { mode: DashboardMode }) {
    const isStaff = mode === "staff";
    const [summary, setSummary] = useState<SpotterSummary | null>(null);
    const [lots, setLots] = useState<string[]>([]);
    const [zoneOptions, setZoneOptions] = useState<ZoneSummary[]>([]);
    const [spaces, setSpaces] = useState<Space[]>([]);
    const [events, setEvents] = useState<DetectionEvent[]>([]);
    const [selectedLot, setSelectedLot] = useState("all");
    const [selectedZone, setSelectedZone] = useState("all");
    const [lastSync, setLastSync] = useState<Date | null>(null);
    const [spotterError, setSpotterError] = useState<string | null>(null);
    const [account, setAccount] = useState<Account | null>(null);
    const [accounts, setAccounts] = useState<Account[]>([]);
    const [accountError, setAccountError] = useState<string | null>(null);
    const [actionStatus, setActionStatus] = useState<string | null>(null);
    const [actionEvents, setActionEvents] = useState<DetectionEvent[]>([]);
    const [actionBusy, setActionBusy] = useState<string | null>(null);
    const [approvalBusyId, setApprovalBusyId] = useState<number | null>(null);
    const [approvalStatus, setApprovalStatus] = useState<string | null>(null);
    const [selectedSensor, setSelectedSensor] = useState("");
    const [manualOccupied, setManualOccupied] = useState("true");

    const loadSpotter = useCallback(async () => {
        try {
            const summaryParams = new URLSearchParams();
            const zoneParams = new URLSearchParams();

            if (selectedLot !== "all") {
                summaryParams.set("lotName", selectedLot);
                zoneParams.set("lotName", selectedLot);
            }

            if (selectedZone !== "all") {
                summaryParams.set("zone", selectedZone);
            }

            const spaceParams = new URLSearchParams(summaryParams);

            if (!isStaff) {
                spaceParams.set("occupied", "false");
            }

            const [nextSummary, nextLots, nextZones, nextSpaces, nextEvents] = await Promise.all([
                spotterFetcher<SpotterSummary>(`/summary${toQuery(summaryParams)}`),
                spotterFetcher<string[]>("/lots"),
                spotterFetcher<ZoneSummary[]>(`/zones${toQuery(zoneParams)}`),
                spotterFetcher<Space[]>(`/spaces${toQuery(spaceParams)}`),
                spotterFetcher<DetectionEvent[]>("/events"),
            ]);

            setSummary(nextSummary);
            setLots(nextLots);
            setZoneOptions(nextZones);
            setSpaces(nextSpaces);
            setEvents(nextEvents);
            setLastSync(new Date());
            setSpotterError(null);
        } catch (error) {
            setSpotterError(error instanceof Error ? error.message : "Spotter service is unavailable");
        }
    }, [isStaff, selectedLot, selectedZone]);

    useEffect(() => {
        const timeout = window.setTimeout(() => void loadSpotter(), 0);
        const interval = window.setInterval(() => void loadSpotter(), 2000);

        return () => {
            window.clearTimeout(timeout);
            window.clearInterval(interval);
        };
    }, [loadSpotter]);

    useEffect(() => {
        let cancelled = false;

        async function loadAccountData() {
            try {
                const profile = await fetcher<Account>("/api/accounts");
                if (!cancelled) {
                    setAccount(profile);
                    setAccountError(null);
                }
            } catch (error) {
                if (error instanceof Error && (error.name === "401" || error.name === "403")) {
                    clearJwtCookie();
                    window.location.href = "/login";
                    return;
                }

                if (!cancelled) {
                    setAccountError(error instanceof Error ? error.message : "Account service is unavailable");
                }
            }

            if (isStaff) {
                try {
                    const staffAccounts = await fetcher<Account[]>("/api/staff/accounts");
                    if (!cancelled) {
                        setAccounts(staffAccounts);
                    }
                } catch {
                    if (!cancelled) {
                        setAccounts([]);
                    }
                }
            }
        }

        void loadAccountData();

        return () => {
            cancelled = true;
        };
    }, [isStaff]);

    const zoneNames = useMemo(() => {
        return Array.from(new Set(zoneOptions.map((zone) => zone.zone))).sort();
    }, [zoneOptions]);

    const visibleZones = useMemo(() => summary?.zones ?? [], [summary]);
    const visibleSpaces = spaces.slice(0, isStaff ? 14 : 12);
    const recentEvents = events.slice(0, 8);
    const staffStats = useMemo(() => accountStats(accounts), [accounts]);
    const pendingApprovals = useMemo(() => {
        return accounts.filter((staffAccount) => (
            (staffAccount.role === "ADMIN" || staffAccount.role === "STAFF")
            && !staffAccount.enabled
            && staffAccount.id !== account?.id
        ));
    }, [account?.id, accounts]);
    const bestZone = useMemo(() => {
        return [...visibleZones]
            .filter((zone) => zone.availableSpaces > 0)
            .sort((first, second) => first.occupancyRate - second.occupancyRate || second.availableSpaces - first.availableSpaces)[0];
    }, [visibleZones]);

    async function approveAccount(accountToApprove: Account) {
        setApprovalBusyId(accountToApprove.id);
        setApprovalStatus(null);

        try {
            await fetcher<void>(`/api/admin/accounts/${accountToApprove.id}/enable`, {
                method: "PATCH",
            });
            setAccounts((currentAccounts) => currentAccounts.map((currentAccount) => (
                currentAccount.id === accountToApprove.id ? { ...currentAccount, enabled: true } : currentAccount
            )));
            setApprovalStatus(`${accountToApprove.username} approved`);
        } catch (error) {
            setApprovalStatus(error instanceof Error ? error.message : "Approval failed");
        } finally {
            setApprovalBusyId(null);
        }
    }

    async function runSimulation(eventCount: number) {
        setActionBusy(`run-${eventCount}`);
        setActionStatus(null);
        setActionEvents([]);

        try {
            const result = await spotterFetcher<SimulationRunResponse>("/simulation/run", {
                method: "POST",
                body: JSON.stringify({
                    eventCount,
                    publishEvents: true,
                }),
            });

            setSummary(result.summary);
            setActionEvents(result.events.map((item) => item.event));
            setActionStatus(`${result.appliedEvents} update${result.appliedEvents === 1 ? "" : "s"} applied`);
            await loadSpotter();
        } catch (error) {
            setActionEvents([]);
            setActionStatus(error instanceof Error ? error.message : "Sensor feed failed");
        } finally {
            setActionBusy(null);
        }
    }

    async function resetSimulation() {
        setActionBusy("reset");
        setActionStatus(null);
        setActionEvents([]);

        try {
            await spotterFetcher<SimulationRunResponse>("/simulation/reset", {
                method: "POST",
            });
            setActionStatus("Parking data reset");
            await loadSpotter();
        } catch (error) {
            setActionEvents([]);
            setActionStatus(error instanceof Error ? error.message : "Reset failed");
        } finally {
            setActionBusy(null);
        }
    }

    async function recordManualReading() {
        const sensorId = selectedSensor || spaces[0]?.sensorId;

        if (!sensorId) {
            return;
        }

        setActionBusy("manual");
        setActionStatus(null);
        setActionEvents([]);

        try {
            const event = await spotterFetcher<DetectionEvent>(`/sensors/${encodeURIComponent(sensorId)}/detect`, {
                method: "POST",
                body: JSON.stringify({
                    occupied: manualOccupied === "true",
                    confidence: 0.99,
                    source: "staff-dashboard",
                }),
            });
            setActionEvents([event]);
            setActionStatus("Manual reading recorded");
            await loadSpotter();
        } catch (error) {
            setActionEvents([]);
            setActionStatus(error instanceof Error ? error.message : "Manual reading failed");
        } finally {
            setActionBusy(null);
        }
    }

    return (
        <div className="min-h-screen bg-[#f7f7f4] text-slate-950">
            <main className="mx-auto flex w-full max-w-7xl flex-col gap-6 px-4 py-6 sm:px-6 lg:px-8">
                <header className="flex flex-col gap-4 border-b border-stone-300 pb-5 lg:flex-row lg:items-end lg:justify-between">
                    <div className="space-y-2">
                        <p className="text-sm font-semibold uppercase tracking-normal text-teal-700">
                            {isStaff ? "Staff dashboard" : "User dashboard"}
                        </p>
                        <h1 className="text-3xl font-semibold tracking-normal text-slate-950 sm:text-4xl">
                            {isStaff ? "Parking operations" : "Find a parking space"}
                        </h1>
                        <div className="flex flex-wrap items-center gap-3 text-sm text-slate-600">
                            <span className="inline-flex items-center gap-2 rounded-md border border-emerald-200 bg-emerald-50 px-3 py-1 font-medium text-emerald-800">
                                <span className="h-2 w-2 rounded-full bg-emerald-500" />
                                Live
                            </span>
                            <span>Last sync {formatTime(lastSync)}</span>
                            {account && <span>{account.username} - {account.role}</span>}
                            <button
                                type="button"
                                onClick={logout}
                                className="rounded-md border border-stone-300 bg-white px-3 py-1 font-medium text-slate-700 transition hover:bg-stone-100"
                            >
                                Logout
                            </button>
                        </div>
                    </div>

                    <div className="flex flex-wrap gap-2">
                        <label className="flex min-w-36 flex-col gap-1 text-sm font-medium text-slate-700">
                            Lot
                            <select
                                value={selectedLot}
                                onChange={(event) => {
                                    setSelectedLot(event.target.value);
                                    setSelectedZone("all");
                                }}
                                className="h-10 rounded-md border border-stone-300 bg-white px-3 text-sm text-slate-950 shadow-sm outline-none focus:border-teal-500"
                            >
                                <option value="all">All lots</option>
                                {lots.map((lot) => (
                                    <option key={lot} value={lot}>
                                        {lot}
                                    </option>
                                ))}
                            </select>
                        </label>

                        <label className="flex min-w-36 flex-col gap-1 text-sm font-medium text-slate-700">
                            Zone
                            <select
                                value={selectedZone}
                                onChange={(event) => setSelectedZone(event.target.value)}
                                className="h-10 rounded-md border border-stone-300 bg-white px-3 text-sm text-slate-950 shadow-sm outline-none focus:border-teal-500"
                            >
                                <option value="all">All zones</option>
                                {zoneNames.map((zone) => (
                                    <option key={zone} value={zone}>
                                        Zone {zone}
                                    </option>
                                ))}
                            </select>
                        </label>
                    </div>
                </header>

                {(spotterError || accountError) && (
                    <section className="rounded-lg border border-amber-300 bg-amber-50 px-4 py-3 text-sm text-amber-900">
                        {spotterError ? `Spotter: ${spotterError}` : `Accounts: ${accountError}`}
                    </section>
                )}

                <section className="grid gap-3 sm:grid-cols-2 lg:grid-cols-4">
                    <Metric label="Available" value={metricValue(summary?.availableSpaces)} detail={`${metricValue(summary?.totalSpaces)} total spaces`} tone="emerald" />
                    <Metric label="Occupied" value={metricValue(summary?.occupiedSpaces)} detail={`${percent(summary?.occupancyRate ?? 0)} occupancy`} tone="rose" />
                    <Metric label="Accessible free" value={metricValue(summary?.availableDisabilityPermitSpaces)} detail={`${metricValue(summary?.disabilityPermitSpaces)} disability-permit bays`} tone="sky" />
                    <Metric
                        label={isStaff ? "Accounts" : "Best zone"}
                        value={isStaff ? metricValue(staffStats.total) : bestZone ? `${bestZone.lotName} ${bestZone.zone}` : "Checking"}
                        detail={isStaff ? `${staffStats.staff} staff/admin, ${staffStats.disabled} disabled` : bestZone ? `${bestZone.availableSpaces} free spaces` : "No spaces match filters"}
                        tone="violet"
                    />
                </section>

                {isStaff && (
                    <section className="grid gap-3 sm:grid-cols-3">
                        <Metric label="User accounts" value={metricValue(staffStats.users)} detail={`${staffStats.premium} premium subscriptions`} tone="sky" />
                        <Metric label="Sensor events" value={metricValue(events.length)} detail="Recent stored readings" tone="amber" />
                        <Metric label="Showing spaces" value={metricValue(spaces.length)} detail={selectedLot === "all" ? "Across all lots" : selectedLot} tone="emerald" />
                    </section>
                )}

                {isStaff && (
                    <section className="grid gap-4 lg:grid-cols-[minmax(0,1fr)_minmax(320px,420px)]">
                        <StaffControls
                            spaces={spaces}
                            selectedSensor={selectedSensor || spaces[0]?.sensorId || ""}
                            manualOccupied={manualOccupied}
                            actionBusy={actionBusy}
                            actionStatus={actionStatus}
                            actionEvents={actionEvents}
                            onSensorChange={setSelectedSensor}
                            onOccupiedChange={setManualOccupied}
                            onRunSimulation={runSimulation}
                            onReset={resetSimulation}
                            onRecordManual={recordManualReading}
                        />
                        <RecentEvents events={recentEvents} totalEvents={events.length} />
                    </section>
                )}

                {isStaff && account?.role === "ADMIN" && (
                    <PendingAccountApprovals
                        accounts={pendingApprovals}
                        approvalBusyId={approvalBusyId}
                        approvalStatus={approvalStatus}
                        onApprove={approveAccount}
                    />
                )}

                <section className="grid gap-4 lg:grid-cols-[minmax(0,0.95fr)_minmax(0,1.05fr)]">
                    <ZoneOverview zones={visibleZones} />
                    <SpaceList spaces={visibleSpaces} isStaff={isStaff} />
                </section>
            </main>
        </div>
    );
}

function Metric({ label, value, detail, tone }: { label: string; value: string; detail: string; tone: "emerald" | "rose" | "sky" | "violet" | "amber" }) {
    const tones = {
        emerald: "border-emerald-200 bg-emerald-50",
        rose: "border-rose-200 bg-rose-50",
        sky: "border-sky-200 bg-sky-50",
        violet: "border-violet-200 bg-violet-50",
        amber: "border-amber-200 bg-amber-50",
    };

    return (
        <div className={`rounded-lg border p-4 shadow-sm ${tones[tone]}`}>
            <p className="text-sm font-medium text-slate-600">{label}</p>
            <p className="mt-2 break-words text-3xl font-semibold tracking-normal text-slate-950">{value}</p>
            <p className="mt-1 text-sm text-slate-600">{detail}</p>
        </div>
    );
}

function StaffControls({
    spaces,
    selectedSensor,
    manualOccupied,
    actionBusy,
    actionStatus,
    actionEvents,
    onSensorChange,
    onOccupiedChange,
    onRunSimulation,
    onReset,
    onRecordManual,
}: {
    spaces: Space[];
    selectedSensor: string;
    manualOccupied: string;
    actionBusy: string | null;
    actionStatus: string | null;
    actionEvents: DetectionEvent[];
    onSensorChange: (value: string) => void;
    onOccupiedChange: (value: string) => void;
    onRunSimulation: (count: number) => Promise<void>;
    onReset: () => Promise<void>;
    onRecordManual: () => Promise<void>;
}) {
    return (
        <section className="rounded-lg border border-stone-300 bg-white p-4 shadow-sm">
            <div className="flex flex-col gap-1">
                <h2 className="text-lg font-semibold text-slate-950">Sensor feed</h2>
                <p className="text-sm text-slate-600">Apply readings to the live parking dataset.</p>
            </div>

            <div className="mt-4 flex flex-wrap gap-2">
                <button
                    type="button"
                    onClick={() => void onRunSimulation(1)}
                    disabled={Boolean(actionBusy)}
                    className="h-10 rounded-md bg-slate-950 px-4 text-sm font-semibold text-white transition hover:bg-slate-800 disabled:cursor-not-allowed disabled:opacity-50"
                >
                    {actionBusy === "run-1" ? "Applying" : "Next event"}
                </button>
                <button
                    type="button"
                    onClick={() => void onRunSimulation(5)}
                    disabled={Boolean(actionBusy)}
                    className="h-10 rounded-md border border-stone-300 bg-white px-4 text-sm font-semibold text-slate-950 transition hover:bg-stone-100 disabled:cursor-not-allowed disabled:opacity-50"
                >
                    {actionBusy === "run-5" ? "Applying" : "Run 5"}
                </button>
                <button
                    type="button"
                    onClick={() => void onReset()}
                    disabled={Boolean(actionBusy)}
                    className="h-10 rounded-md border border-rose-200 bg-rose-50 px-4 text-sm font-semibold text-rose-800 transition hover:bg-rose-100 disabled:cursor-not-allowed disabled:opacity-50"
                >
                    {actionBusy === "reset" ? "Resetting" : "Reset"}
                </button>
            </div>

            <div className="mt-5 grid gap-3 sm:grid-cols-[minmax(0,1fr)_140px_auto]">
                <label className="flex min-w-0 flex-col gap-1 text-sm font-medium text-slate-700">
                    Sensor
                    <select
                        value={selectedSensor}
                        onChange={(event) => onSensorChange(event.target.value)}
                        className="h-10 rounded-md border border-stone-300 bg-white px-3 text-sm text-slate-950 outline-none focus:border-teal-500"
                    >
                        {spaces.map((space) => (
                            <option key={space.sensorId} value={space.sensorId}>
                                {space.sensorId}
                            </option>
                        ))}
                    </select>
                </label>
                <label className="flex flex-col gap-1 text-sm font-medium text-slate-700">
                    Status
                    <select
                        value={manualOccupied}
                        onChange={(event) => onOccupiedChange(event.target.value)}
                        className="h-10 rounded-md border border-stone-300 bg-white px-3 text-sm text-slate-950 outline-none focus:border-teal-500"
                    >
                        <option value="true">Occupied</option>
                        <option value="false">Free</option>
                    </select>
                </label>
                <button
                    type="button"
                    onClick={() => void onRecordManual()}
                    disabled={Boolean(actionBusy) || !selectedSensor}
                    className="mt-6 h-10 rounded-md bg-teal-700 px-4 text-sm font-semibold text-white transition hover:bg-teal-800 disabled:cursor-not-allowed disabled:opacity-50 sm:mt-auto"
                >
                    {actionBusy === "manual" ? "Saving" : "Record"}
                </button>
            </div>

            {actionStatus && <p className="mt-3 text-sm font-medium text-slate-700">{actionStatus}</p>}
            {actionEvents.length > 0 && (
                <div className="mt-3 divide-y divide-stone-200 rounded-md border border-stone-200 bg-stone-50">
                    {actionEvents.map((event) => (
                        <div key={event.id} className="grid gap-2 px-3 py-2 text-sm sm:grid-cols-[minmax(0,1fr)_auto]">
                            <div className="min-w-0">
                                <p className="truncate font-semibold text-slate-950">
                                    {event.sensorId} - {eventChangeText(event)}
                                </p>
                                <p className="truncate text-xs text-slate-600">
                                    {event.lotName} - Zone {event.zone} - Bay {event.bayNumber}
                                </p>
                            </div>
                            <div className="flex items-center gap-2 sm:justify-end">
                                <OccupancyPill occupied={event.occupied} />
                                <span className="text-xs text-slate-500">{formatTime(event.detectedAt)}</span>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </section>
    );
}

function RecentEvents({ events, totalEvents }: { events: DetectionEvent[]; totalEvents: number }) {
    return (
        <section className="rounded-lg border border-stone-300 bg-white p-4 shadow-sm">
            <div className="flex items-center justify-between gap-3">
                <div>
                    <h2 className="text-lg font-semibold text-slate-950">Recent events</h2>
                    {totalEvents > events.length && <p className="text-xs text-slate-500">Showing latest {events.length} of {totalEvents}</p>}
                </div>
                <span className="rounded-md bg-slate-100 px-2 py-1 text-xs font-semibold text-slate-600">{totalEvents}</span>
            </div>
            <div className="mt-3 divide-y divide-stone-200">
                {events.length === 0 && <p className="py-6 text-sm text-slate-600">No sensor readings yet.</p>}
                {events.map((event) => (
                    <div key={event.id} className="grid grid-cols-[minmax(0,1fr)_auto] gap-3 py-3">
                        <div className="min-w-0">
                            <p className="truncate text-sm font-semibold text-slate-950">
                                {event.lotName} - Zone {event.zone} - Bay {event.bayNumber}
                            </p>
                            <p className="truncate text-xs text-slate-600">
                                {event.sensorId} - {event.source} - {eventChangeText(event)}
                            </p>
                        </div>
                        <div className="text-right">
                            <OccupancyPill occupied={event.occupied} />
                            <p className="mt-1 text-xs text-slate-500">{formatTime(event.detectedAt)}</p>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
}

function PendingAccountApprovals({
    accounts,
    approvalBusyId,
    approvalStatus,
    onApprove,
}: {
    accounts: Account[];
    approvalBusyId: number | null;
    approvalStatus: string | null;
    onApprove: (account: Account) => Promise<void>;
}) {
    return (
        <section className="rounded-lg border border-stone-300 bg-white p-4 shadow-sm">
            <div className="flex items-center justify-between gap-3">
                <h2 className="text-lg font-semibold text-slate-950">Staff/admin approvals</h2>
                <span className="rounded-md bg-slate-100 px-2 py-1 text-xs font-semibold text-slate-600">{accounts.length}</span>
            </div>

            {approvalStatus && <p className="mt-3 text-sm font-medium text-slate-700">{approvalStatus}</p>}

            <div className="mt-3 divide-y divide-stone-200">
                {accounts.length === 0 && <p className="py-4 text-sm text-slate-600">No pending staff or admin accounts.</p>}
                {accounts.map((adminAccount) => (
                    <div key={adminAccount.id} className="grid gap-3 py-3 sm:grid-cols-[minmax(0,1fr)_auto] sm:items-center">
                        <div className="min-w-0">
                            <div className="flex flex-wrap items-center gap-2">
                                <p className="truncate text-sm font-semibold text-slate-950">{adminAccount.username}</p>
                                <span className="rounded-md bg-stone-100 px-2 py-1 text-xs font-semibold text-slate-600">{adminAccount.role}</span>
                            </div>
                            <p className="truncate text-xs text-slate-600">{adminAccount.email}</p>
                        </div>
                        <button
                            type="button"
                            onClick={() => void onApprove(adminAccount)}
                            disabled={approvalBusyId !== null}
                            className="h-10 rounded-md bg-teal-700 px-4 text-sm font-semibold text-white transition hover:bg-teal-800 disabled:cursor-not-allowed disabled:opacity-50"
                        >
                            {approvalBusyId === adminAccount.id ? "Approving" : "Approve"}
                        </button>
                    </div>
                ))}
            </div>
        </section>
    );
}

function ZoneOverview({ zones }: { zones: ZoneSummary[] }) {
    return (
        <section className="rounded-lg border border-stone-300 bg-white p-4 shadow-sm">
            <div className="flex items-center justify-between gap-3">
                <h2 className="text-lg font-semibold text-slate-950">Zone availability</h2>
                <span className="rounded-md bg-slate-100 px-2 py-1 text-xs font-semibold text-slate-600">{zones.length}</span>
            </div>
            <div className="mt-4 space-y-3">
                {zones.length === 0 && <p className="py-8 text-sm text-slate-600">No zones match the current filters.</p>}
                {zones.map((zone) => (
                    <div key={`${zone.lotName}-${zone.zone}`} className="rounded-md border border-stone-200 p-3">
                        <div className="flex items-center justify-between gap-3">
                            <div>
                                <p className="text-sm font-semibold text-slate-950">{zone.lotName} - Zone {zone.zone}</p>
                                <p className="text-xs text-slate-600">{zone.availableSpaces} free of {zone.totalSpaces}</p>
                            </div>
                            <p className="text-sm font-semibold text-slate-700">{percent(zone.occupancyRate)}</p>
                        </div>
                        <div className="mt-3 h-2 overflow-hidden rounded-md bg-stone-100">
                            <div
                                className={`h-full rounded-md ${zone.occupancyRate > 80 ? "bg-rose-500" : zone.occupancyRate > 55 ? "bg-amber-500" : "bg-emerald-500"}`}
                                style={{ width: `${Math.min(zone.occupancyRate, 100)}%` }}
                            />
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
}

function SpaceList({ spaces, isStaff }: { spaces: Space[]; isStaff: boolean }) {
    return (
        <section className="rounded-lg border border-stone-300 bg-white p-4 shadow-sm">
            <div className="flex items-center justify-between gap-3">
                <h2 className="text-lg font-semibold text-slate-950">{isStaff ? "Space status" : "Available spaces"}</h2>
                <span className="rounded-md bg-slate-100 px-2 py-1 text-xs font-semibold text-slate-600">{spaces.length}</span>
            </div>
            <div className="mt-4 grid gap-3 sm:grid-cols-2">
                {spaces.length === 0 && <p className="py-8 text-sm text-slate-600">No spaces match the current filters.</p>}
                {spaces.map((space) => (
                    <article key={space.id} className="rounded-md border border-stone-200 p-3">
                        <div className="flex items-start justify-between gap-3">
                            <div className="min-w-0">
                                <p className="truncate text-sm font-semibold text-slate-950">{space.displayName}</p>
                                <p className="truncate text-xs text-slate-600">{space.sensorId}</p>
                            </div>
                            <OccupancyPill occupied={space.occupied} />
                        </div>
                        <div className="mt-3 grid grid-cols-3 gap-2 text-xs text-slate-600">
                            <div>
                                <p className="font-semibold text-slate-900">{space.lotName}</p>
                                <p>Lot</p>
                            </div>
                            <div>
                                <p className="font-semibold text-slate-900">Zone {space.zone}</p>
                                <p>Area</p>
                            </div>
                            <div>
                                <p className="font-semibold text-slate-900">{space.maxParkingMinutes}m</p>
                                <p>Limit</p>
                            </div>
                        </div>
                        <div className="mt-3 flex flex-wrap items-center gap-2 text-xs text-slate-500">
                            {space.disabilityPermitRequired && (
                                <span className="rounded-md bg-sky-50 px-2 py-1 font-semibold text-sky-800">Permit bay</span>
                            )}
                            <span>{Math.round(space.confidence * 100)}% confidence</span>
                            <span>{formatDateTime(space.lastUpdated)}</span>
                        </div>
                    </article>
                ))}
            </div>
        </section>
    );
}

function OccupancyPill({ occupied }: { occupied: boolean }) {
    return (
        <span className={`whitespace-nowrap rounded-md px-2 py-1 text-xs font-semibold ${occupied ? "bg-rose-50 text-rose-800" : "bg-emerald-50 text-emerald-800"}`}>
            {occupied ? "Occupied" : "Free"}
        </span>
    );
}
